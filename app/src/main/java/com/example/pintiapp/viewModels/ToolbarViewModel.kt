package com.example.pintiapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ToolbarViewModel:ViewModel() {
    val backIconEnabled = MutableLiveData<Boolean>()
    val searchIconEnabled = MutableLiveData<Boolean>()
}