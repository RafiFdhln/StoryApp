package com.example.storyapp.UI.Login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.Helper.UserPreference
import com.example.storyapp.Helper.ViewModelFactory
import com.example.storyapp.UI.Home.HomeActivity
import com.example.storyapp.UI.Register.RegisterActivity
import com.example.storyapp.databinding.ActivityLoginBinding

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private var passwordError = MutableLiveData<Boolean>()
    private var emailError = MutableLiveData<Boolean>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = UserPreference.getInstance(dataStore)
        viewModel = ViewModelProvider(this, ViewModelFactory(pref))[LoginViewModel::class.java]
        supportActionBar?.hide()
        customEmail()
        customPassword()
        checkInput()
        playAnimation()
        toRegister()
    }

    private fun saveUser(){
        viewModel.loginResult.observe(this){
            viewModel.saveUser(it)
            viewModel.login()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        viewModel.loginError.observe(this){
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun checkInput(){
        passwordError.observe(this){
            if(!it && emailError.value == false){
                binding.btnLogin.isEnabled = true
                getLogin()
            }else{
                binding.btnLogin.isEnabled = false
            }
        }
        emailError.observe(this){
            if(!it && passwordError.value == false){
                binding.btnLogin.isEnabled = true
                getLogin()
            }else{
                binding.btnLogin.isEnabled = false
            }

        }
    }

    private fun toRegister(){
        binding.btnDaftar.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
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

    private fun getLogin(){
            binding.btnLogin.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()

                viewModel.loginUser(email, password)
                saveUser()
            }
    }

    private fun playAnimation() {
        binding.apply {
            val welcome = ObjectAnimator.ofFloat(textView, View.ALPHA, 1f).setDuration(250)
            val haveAccount = ObjectAnimator.ofFloat(textView2, View.ALPHA, 1f).setDuration(250)
            val email = ObjectAnimator.ofFloat(etEmail, View.ALPHA, 1f).setDuration(250)
            val password = ObjectAnimator.ofFloat(etPassword, View.ALPHA, 1f).setDuration(250)
            val btnLogin = ObjectAnimator.ofFloat(btnLogin, View.ALPHA, 1f).setDuration(250)
            val register = ObjectAnimator.ofFloat(btnDaftar, View.ALPHA, 1f).setDuration(250)

            AnimatorSet().apply {
                playSequentially(
                    welcome,
                    email,
                    password,
                    btnLogin,
                    haveAccount,
                    register,
                )
                start()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
