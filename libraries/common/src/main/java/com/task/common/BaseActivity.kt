package com.task.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), UiCommunicator {

    private var progress: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        setUp()
    }


    override fun showLoading() {
        progress?.dismiss() ?: kotlin.run {
            progress = progressDialog(this)
        }
        progress?.show()
    }

    override fun hideLoading() {
        progress?.dismiss()
    }

    override fun handleMessages(messageType: MessageType) {
        hideLoading()
        var message = ErrorMessageHelper.getMessage(messageType.code)
        messageType.message?.let { message = it }
        when (messageType) {
            is MessageType.SnackBar -> {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
            is MessageType.Dialog -> {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
            is MessageType.None -> TODO()
            is MessageType.Toast -> TODO()
        }

    }


    //all of this function needs future impl

    protected abstract fun setUp()
    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB

    protected val binding: VB
        get() = _binding as VB


    private fun progressDialog(context: Context): Dialog {
        val dialog = Dialog(context)
        val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        dialog.setContentView(inflate)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}