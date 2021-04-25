package com.android.pxapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.pxapp.data.model.Message
import com.android.pxapp.data.model.MessageData
import com.android.pxapp.util.prefs
import com.android.pxapp.util.Prefs.deleteMessageSet

class MainViewModel : ViewModel() {

//    private var _messageData = MutableLiveData<MessageData>()
//
//
//    fun getMessageData(): LiveData<MessageData> {
//        return _messageData
//    }
//
//    fun setMessageData(messageData: MessageData) {
//        _messageData.postValue(messageData)
//    }
//
//    fun removeSelect(checkMessageSet: Set<Message>) {
//        _messageData.value?.let {
//            val list = mutableListOf<Message>()
//            val titleSet = HashSet<String>()
//            for(msg in it.messageResult.messages) {
//                if(!checkMessageSet.contains(msg)){
//                    list.add(msg)
//                }
//            }
//            it.messageResult.messages = list
//            setMessageData(it)
//            for (msg in checkMessageSet) {
//                titleSet.add(msg.title)
//            }
//            prefs.deleteMessageSet = titleSet
//        }
//    }

}