package com.example.storyapp.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.Helper.UserPreference
import com.example.storyapp.Helper.ViewModelFactory
import com.example.storyapp.UI.Home.HomeActivity
import com.example.storyapp.UI.Login.LoginActivity
import com.example.storyapp.UI.Login.LoginViewModel
import com.example.storyapp.UI.Login.dataStore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pref = UserPreference.getInstance(dataStore)
        val ViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            LoginViewModel::class.java
        )
        ViewModel.isLogin().observe(this) { isLogin : Boolean ->
            if(isLogin){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}