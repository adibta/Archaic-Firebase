package com.archaic.firechaic.data.firebase.callback

interface LoadItemCallback<T> {
    fun onLoaded(item: T)
    fun onFailed()
}