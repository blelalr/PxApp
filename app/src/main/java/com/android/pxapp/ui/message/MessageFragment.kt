package com.android.pxapp.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.pxapp.data.model.Message
import com.android.pxapp.data.model.MessageData
import com.android.pxapp.data.network.ApiState
import com.android.pxapp.ui.message.ToolbarState.*
import android.view.View.GONE
import android.view.View.VISIBLE
import com.android.pxapp.databinding.FragmentMessageBinding
import com.android.pxapp.databinding.ToolbarDefaultBinding
import com.android.pxapp.databinding.ToolbarEditBinding


class MessageFragment : Fragment(){
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!
    private val messageViewModel : MessageViewModel by viewModels()
    private val messageListAdapter by lazy { MessageListAdapter { message: Message, isCheck: Boolean ->  messageViewModel.updateCheckSet(message, isCheck) } }
    private val toolbarDefaultBinding by lazy { ToolbarDefaultBinding.inflate( LayoutInflater.from(context), binding.root,false)}
    private val toolbarEditBinding by lazy { ToolbarEditBinding.inflate( LayoutInflater.from(context), binding.root,false)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupObserver()
    }

    private fun setupObserver() {
        messageViewModel.getMessagesFromRepo().observe(viewLifecycleOwner, Observer {
            setViewByState(it)
        })

        messageViewModel.getMessageData().observe(viewLifecycleOwner, Observer {
            messageListAdapter.submitList(it.messageResult.messages)
        })

        messageViewModel.getToolbarState().observe(viewLifecycleOwner, Observer {
            setToolBarByState(it)
        })
        messageViewModel.getIsSelectAll().observe(viewLifecycleOwner, Observer {
            setSelectAll(it)
        })
    }

    private fun setViewByState(state: ApiState) {
        when(state) {
            is ApiState.Loading -> {
                binding.progressCircular.visibility = VISIBLE
                binding.messageList.visibility = GONE
            }
            is ApiState.Success<*> -> {
                binding.progressCircular.visibility = GONE
                binding.messageList.visibility = VISIBLE
                messageViewModel.setMessageData(state.data as MessageData)
            }
            is ApiState.Failed<*> -> {
                binding.progressCircular.visibility = GONE
            }
        }
    }

    private fun setToolBarByState(state: ToolbarState) {
        binding.toolbarContentContainer.removeAllViews()

        when (state) {
            is DefaultViewState -> {
                binding.toolbarContentContainer.addView(toolbarDefaultBinding.toolbarDefault)
                binding.editModeBar.visibility = GONE
                toolbarDefaultBinding.toolbarTitle.text = "Message"
                toolbarDefaultBinding.toolbarRight.text = "Edit"
            }
            is EditState -> {
                binding.toolbarContentContainer.addView(toolbarEditBinding.multiselectToolbar)
                binding.editModeBar.visibility = VISIBLE
            }
        }

        messageListAdapter.apply { viewState = state }
        setSelectAll(true)
    }

    private fun setSelectAll(it :Boolean) {
        if(it) {
            binding.selectAll.text = "SelectAll"
        } else {
            binding.selectAll.text = "ClearSelect"
        }
        messageListAdapter.apply { selectAll = !it }
        messageListAdapter.notifyDataSetChanged()

    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.messageList.layoutManager = linearLayoutManager
        binding.messageList.adapter = messageListAdapter

        toolbarDefaultBinding.toolbarRight.setOnClickListener {
            messageViewModel.setToolBarState(EditState)
        }

        toolbarEditBinding.actionDisableEditState.setOnClickListener {
            messageViewModel.setToolBarState(DefaultViewState)
        }

        binding.actionDeleteMessage.setOnClickListener {
            messageViewModel.removeSelect()
            messageViewModel.setToolBarState(DefaultViewState)
        }

        binding.selectAll.setOnClickListener {
            messageViewModel.setIsSelectAll(messageListAdapter.selectAll)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}