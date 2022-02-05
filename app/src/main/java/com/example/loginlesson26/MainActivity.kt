package com.example.loginlesson26


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.loginlesson26.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val preferences by lazy {
        CustomPreference(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setSupportActionBar(binding.toolbar)
        if (preferences.login == "" && preferences.password == "") {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, LoginFragment.newInstance())
                .commit()
        } else {
            preferences.login = ""
            preferences.password = ""
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, TrackListFragment.newInstance())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onLogoutClick()
        return true
    }

    private fun onLogoutClick() {
        preferences.login = ""
        preferences.password = ""
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, LoginFragment.newInstance())
            .commit()
    }
}
