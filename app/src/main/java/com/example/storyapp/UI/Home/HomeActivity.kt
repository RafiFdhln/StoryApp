package com.example.storyapp.UI.Home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.Adapter.LoadingStateAdapter
import com.example.storyapp.Adapter.StoryAdapter
import com.example.storyapp.Helper.UserPreference
import com.example.storyapp.Network.ListStoryResult
import com.example.storyapp.Network.LoginResult
import com.example.storyapp.R
import com.example.storyapp.UI.Add.AddActivity
import com.example.storyapp.UI.Detail.DetailActivity
import com.example.storyapp.UI.Location.MapsActivity
import com.example.storyapp.UI.Login.dataStore
import com.example.storyapp.UI.MainActivity
import com.example.storyapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private var user: LoginResult? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        setUser()
        getData()
        addStory()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun setUser(){
        val pref = UserPreference.getInstance(dataStore)

        viewModel = ViewModelProvider(this, HomeViewModelFactory(pref, applicationContext))[HomeViewModel::class.java]
        viewModel.getUser(){
            user = it
        }
    }

    private fun getData() {
        val adapter = StoryAdapter {
            moveDetailActivity(it)
        }
        val pref = UserPreference.getInstance(dataStore)

        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        viewModel = ViewModelProvider(this, HomeViewModelFactory(pref, applicationContext))[HomeViewModel::class.java]
        user?.let { user ->
            viewModel.getStory(user.token).observe(this@HomeActivity) {
                adapter.submitData(lifecycle, it)
            }
        }
    }

    private fun moveDetailActivity(data: ListStoryResult) {
        val bundle = Bundle().apply {
            putParcelable("STORY", data)
        }
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
        onPause()
    }

    private fun addStory(){
        binding.fabFavorite.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            onPause()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            R.id.logout -> {
                viewModel.logout()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            R.id.location -> {
                startActivity(Intent(this, MapsActivity::class.java))
                onPause()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}