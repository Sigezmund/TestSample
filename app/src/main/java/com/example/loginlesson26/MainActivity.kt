package com.example.loginlesson26


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.work.*
import com.example.loginlesson26.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit


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
        val periodicConstraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorker= PeriodicWorkRequestBuilder<RefreshDataWorker>(12,TimeUnit.HOURS)
            .addTag("PeriodicWorker")
            .setConstraints(periodicConstraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        WorkManager.getInstance(this).enqueue(periodicWorker)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
