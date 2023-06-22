package com.example.storyapp.CustomView

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

class MyEmail : AppCompatEditText {
    private val message = MutableLiveData<String>()
    private val hideError = MutableLiveData<Boolean>()
    private val error = MutableLiveData<Boolean>()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                when {
                    s.isEmpty() -> {
                        setMessage("Email is required")
                        setError(true)
                    }
                    Patterns.EMAIL_ADDRESS.matcher(s).matches() -> {
                        hideErrorMessage()
                        setError(false)
                    }
                    else -> {
                        setMessage("Email invalid")
                        setError(true)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }

    fun onValidateInput(
        activity: Activity,
        hideErrorMessage: () -> Unit,
        setMessage: (message: String) -> Unit,
        setError: (error: Boolean) -> Unit
    ) {
        hideError.observe(activity as LifecycleOwner) { hideErrorMessage() }
        message.observe(activity as LifecycleOwner) { setMessage(it) }
        error.observe(activity as LifecycleOwner) {setError(it)}
    }

    private fun hideErrorMessage() {
        hideError.value = true
    }

    private fun setMessage(it: String) {
        message.value = it
    }

    private fun setError(it: Boolean) {
        error.value = it
    }
}