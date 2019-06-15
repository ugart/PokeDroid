package com.example.pokedroid.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.view.View

abstract class BaseViewModel : ViewModel() {

    val onError = SingleLiveEvent<StringWrapper>()

    fun setupErrorSnackbar(owner: LifecycleOwner,
                           context: Context?,
                           view: View,
                           onCall: () -> Unit = {}){

        onError.observe(owner, Observer {
            if(it != null){
                onCall()
                //showSnackbar(SnackbarType.ERROR, view, it.getMessage(context))
            }
        })
    }

}