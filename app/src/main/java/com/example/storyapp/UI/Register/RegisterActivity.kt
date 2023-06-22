package com.example.storyapp.UI.Register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.UI.Login.LoginActivity
import com.example.storyapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private var passwordError = MutableLiveData<Boolean>()
    private var emailError = MutableLiveData<Boolean>()
    private var nameError = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[RegisterViewModel::class.java]
        supportActionBar?.hide()
        toLogin()
        customPassword()
        customEmail()
        customName()
        checkInput()
        playAnimation()
    }

    private fun customPassword(){
        binding.etPassword.onValidateInput(this,
            hideErrorMessage = {
            },
            setMessage = {
                binding.etPassword.error = it
            },
            setError = {
                passwordError.value = it
            }
        )
    }

    private fun customEmail() {
        binding.etEmail.onValidateInput(this,
            hideErrorMessage = {
            },
            setMessage = {
                binding.etEmail.error = it
            },
            setError = {
                emailError.value = it
            }
        )
    }
    private fun customName() {
        binding.etNama.onValidateInput(this,
            hideErrorMessage = {
            },
            setMessage = {
                binding.etNama.error = it
            },
            setError = {
                nameError.value = it
            }
        )
    }

    private fun checkInput(){
        passwordError.observe(this){
            if(!it && emailError.value == false && nameError.value == false){
                binding.btnDaftar.isEnabled = true
                getRegister()
            }else{
                binding.btnDaftar.isEnabled = false
            }
        }
        emailError.observe(this){
            if(!it && passwordError.value == false && nameError.value == false){
                binding.btnDaftar.isEnabled = true
                getRegister()
            }else{
                binding.btnDaftar.isEnabled = false
            }
        }
        nameError.observe(this){
            if(!it && passwordError.value == false && emailError.value == false){
                binding.btnDaftar.isEnabled = true
                getRegister()
            }else{
                binding.btnDaftar.isEnabled = false
            }

        }
    }

    private fun getRegister(){
        binding.btnDaftar.setOnClickListener {
            val name = binding.etNama.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            viewModel.registerUser(name,email, password)
            registerMessage()

            viewModel.isLoading.observe(this) {
                showLoading(it)
            }
        }
    }

    private fun registerMessage(){
        viewModel.loginError.observe(this){
            if (it){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        viewModel.loginMessage.observe(this){
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun toLogin(){
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun playAnimation() {
        binding.apply {
            val welcome = ObjectAnimator.ofFloat(textView, View.ALPHA, 1f).setDuration(250)
            val haveAccount = ObjectAnimator.ofFloat(textView2, View.ALPHA, 1f).setDuration(250)
            val email = ObjectAnimator.ofFloat(etEmail, View.ALPHA, 1f).setDuration(250)
            val password = ObjectAnimator.ofFloat(etPassword, View.ALPHA, 1f).setDuration(250)
            val name = ObjectAnimator.ofFloat(etNama, View.ALPHA, 1f).setDuration(250)
            val btnLogin = ObjectAnimator.ofFloat(btnLogin, View.ALPHA, 1f).setDuration(250)
            val register = ObjectAnimator.ofFloat(btnDaftar, View.ALPHA, 1f).setDuration(250)

            AnimatorSet().apply {
                playSequentially(
                    welcome,
                    name,
                    email,
                    password,
                    register,
                    haveAccount,
                    btnLogin,
                )
                start()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}