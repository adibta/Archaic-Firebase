package com.archaic.firechaic.data.firebase.callback

import com.archaic.firechaic.data.database.model.Message
import com.archaic.firechaic.data.firebase.model.FireMessage

interface LoadMessageCallback {
    fun onLoaded(message: Message)
    fun onInterrupt(action: FireMessage.FireMessageAction, messageId: String)
    fun onFailed()
}