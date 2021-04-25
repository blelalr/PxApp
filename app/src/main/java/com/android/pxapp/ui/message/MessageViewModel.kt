package com.android.pxapp.ui.message

import androidx.lifecycle.*
import com.android.pxapp.data.model.Message
import com.android.pxapp.data.model.MessageData
import com.android.pxapp.data.network.ApiState
import com.android.pxapp.data.repository.AppRepo
import com.android.pxapp.ui.message.ToolbarState.*
import com.android.pxapp.util.Prefs.deleteMessageSet
import com.android.pxapp.util.prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow

class MessageViewModel(private val appRepo : AppRepo = AppRepo()) : ViewModel() {

    private val _messageData = MutableLiveData<MessageData>()
    private val _toolbarState = MutableLiveData<ToolbarState>(DefaultViewState)
    private val _checkMessageSet: MutableSet<String> = HashSet()
    private val _isSelectAll = MutableLiveData<Boolean>()

    fun setToolBarState(toolbarState: ToolbarState) {
        if(toolbarState is DefaultViewState) _checkMessageSet.clear()
        _toolbarState.value = toolbarState
    }

    fun getToolbarState(): LiveData<ToolbarState> {
        return _toolbarState
    }

    fun updateCheckSet(message: Message, isCheck: Boolean) {
        if(isCheck){
            _checkMessageSet.add(message.ts)
        } else {
            if(_checkMessageSet.contains(message.ts)){
                _checkMessageSet.remove(message.ts)
            }
        }
    }

    fun setIsSelectAll(isSelectAll: Boolean) {
        _messageData.value?.let {
            if(isSelectAll)
                for(msg in it.messageResult.messages) _checkMessageSet.add(msg.ts)
            else
                _checkMessageSet.clear()

            _isSelectAll.value = isSelectAll
        }
    }

    fun getMessagesFromRepo(): LiveData<ApiState>{
       return flow {
            emit(ApiState.Loading)
            emit(appRepo.loadMessageData())
        }.asLiveData(Dispatchers.IO)
    }


    fun getMessageData(): LiveData<MessageData> {
        return _messageData
    }

    fun setMessageData(messageData: MessageData) {
        val list = mutableListOf<Message>()
        for(msg in messageData.messageResult.messages) {
            if(!prefs.deleteMessageSet.contains(msg.ts)){
                list.add(msg)
            }
        }
        messageData.messageResult.messages = list
        _messageData.postValue(messageData)
    }

    fun removeSelect() {
        _messageData.value?.let {
            val tsSet = prefs.deleteMessageSet
            for(ts in _checkMessageSet) {
                tsSet.add(ts)
            }
            prefs.deleteMessageSet = tsSet
            setMessageData(it)
        }
    }

    fun getIsSelectAll() : LiveData<Boolean> {
        return _isSelectAll
    }

}