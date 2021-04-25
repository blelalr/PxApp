package com.android.pxapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.android.pxapp.data.model.BannerData
import com.android.pxapp.data.network.ApiState
import com.android.pxapp.data.repository.AppRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow

class HomeViewModel(private val appRepo : AppRepo = AppRepo()): ViewModel() {
    private val _bannerData = MutableLiveData<BannerData>()

    fun getBannersFromRepo(): LiveData<ApiState> {
        return flow {
            emit(ApiState.Loading)
            emit(appRepo.loadBannersData())
        }.asLiveData(Dispatchers.IO)
    }

    fun getBanners(): LiveData<BannerData> {
        return _bannerData
    }

    fun setBannerData(bannerData: BannerData) {
        _bannerData.postValue(bannerData)
    }


}