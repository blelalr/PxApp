package com.android.pxapp.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.android.pxapp.R
import com.android.pxapp.data.model.Banner
import com.android.pxapp.data.model.BannerData
import com.android.pxapp.data.network.ApiState
import com.android.pxapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val bannerListAdapter by lazy { BannerListAdapter { banner: Banner ->  openUrlBrowser(banner.targetUrl) } }
    private val homeViewModel : HomeViewModel by viewModels()
    private val navController by lazy { NavHostFragment.findNavController(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupObserver()
    }

    private fun setupObserver() {
        if(homeViewModel.getBanners().value == null){
            homeViewModel.getBannersFromRepo().observe(viewLifecycleOwner, Observer {
                setViewByState(it)
            })
        } else {
            bannerListAdapter.submitList(homeViewModel.getBanners().value!!.bannerResult.banners)
        }

        homeViewModel.getBanners().observe(viewLifecycleOwner, Observer {
            bannerListAdapter.submitList(it.bannerResult.banners)
        })

    }

    private fun setViewByState(state: ApiState) {
        when(state) {
            is ApiState.Loading -> {
                binding.progressBar.visibility = VISIBLE
                binding.clContent.visibility = GONE
            }
            is ApiState.Success<*> -> {
                binding.progressBar.visibility = GONE
                binding.clContent.visibility = VISIBLE
                homeViewModel.setBannerData(state.data as BannerData)
            }
            is ApiState.Failed<*> -> {
                binding.progressBar.visibility = GONE
            }
        }
    }

    private fun initView() {
        binding.toolbarDefault.toolbarTitle.text = "PX APP"
        binding.toolbarDefault.toolbarRight.text = "Message"
        binding.toolbarDefault.toolbarRight.setOnClickListener(this)
        binding.vpBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.vpBanner.offscreenPageLimit = 3
        binding.vpBanner.adapter = bannerListAdapter
        TabLayoutMediator(binding.intoTabLayout, binding.vpBanner){ _, _ ->}.attach()
        binding.btnOpenPayment.setOnClickListener(this)

    }

    private fun openUrlBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_open_payment -> {
                navController.currentDestination?.getAction(R.id.action_mainFragment_to_paymentFragment)?.let {
                    navController.navigate(HomeFragmentDirections.actionMainFragmentToPaymentFragment())
                }
            }
            R.id.toolbar_right -> {
                navController.currentDestination?.getAction(R.id.action_mainFragment_to_messageFragment)?.let {
                    navController.navigate(HomeFragmentDirections.actionMainFragmentToMessageFragment())
                }
            }
        }
    }
}