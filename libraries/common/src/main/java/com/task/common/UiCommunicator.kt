package com.task.common

interface UiCommunicator {
    fun showLoading()
    fun hideLoading()
    fun handleMessages(messageType: MessageType)
}