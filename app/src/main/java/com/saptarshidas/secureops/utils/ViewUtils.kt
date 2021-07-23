package com.saptarshidas.secureops.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.saptarshidas.secureops.data.network.Resource
import retrofit2.HttpException

fun<T : Activity> Activity.startNewActivity(activity: Class<T>){

    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}


fun View.visible(isVisible: Boolean){
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.snackBar(message: String, action: (() -> Unit)? = null){
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    // if action is not null
    action?.let {
        snackbar.setAction("retry") { it() }
    }
    snackbar.show()
}

fun Fragment.handleApiError(failure: Resource.Error, retry: ()-> Unit){

    when{
        failure.isNetworkError -> {
            requireView().snackBar("Please check network connection", retry)
        }

        failure.errorCode == 403 -> requireView().snackBar("Invalid username or password")

        else -> {
            requireView().snackBar(failure.errorBody?.string().toString())
        }
    }
}

fun EditText.setReadOnly(value: Boolean, inputType: Int = InputType.TYPE_NULL) {
    isFocusable = !value
    isFocusableInTouchMode = !value
    this.inputType = inputType
}



fun View.enable(enabled: Boolean){
    isEnabled = enabled
    alpha = if (enabled) 1f else  0.5f
}

fun Fragment.checkEdTextValidation(list: ArrayList<EditText>): Boolean{

    for(edTxt in list){
        if(edTxt.text.isEmpty()){
            edTxt.error = "This field can't be empty"
            return false
        }
    }

    return true

}

fun Fragment.checkAutoTxtViewValidation(list: ArrayList<AutoCompleteTextView>): Boolean{
    for(txtView in list){
        if(txtView.text.isEmpty()) {
            txtView.error = "This field can't be empty"
            return false
        }
    }

    return true
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun TextView.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun Fragment.progressDialog(title: String, msg: String): ProgressDialog{
    val dialog = ProgressDialog(requireContext())
    dialog.setTitle(title)
    dialog.setMessage(msg)
    return dialog
}
