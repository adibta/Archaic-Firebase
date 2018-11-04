package com.archaic.firechaic.data.firebase.callback

import com.archaic.firechaic.data.firebase.model.FireMessage

interface LoadItemCallback<T> {
    fun onLoaded(item: T)
    fun onFailed()
}