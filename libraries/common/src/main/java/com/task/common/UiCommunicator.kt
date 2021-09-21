package com.task.common

// this class used to communicate between activity and fragment
interface UiCommunicator {
    fun showLoading()
    fun hideLoading()
    fun handleMessages(messageType: MessageType)
}
