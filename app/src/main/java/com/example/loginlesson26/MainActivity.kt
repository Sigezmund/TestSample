package com.example.loginlesson26


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import com.example.loginlesson26.databinding.ActivityMainBinding
import com.example.loginlesson26.presentation.LoginFragment
import com.example.loginlesson26.presentation.TrackListFragment
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private val preferences = CustomPreference(this)
//    private val loginManager = LoginManager(preferences)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            if (!loginManager.isLoggedIn) {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, LoginFragment.newInstance())
                    .commit()
            } else {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, TrackListFragment.newInstance())
                    .commit()
            }
        }
        if (loginManager.isLoggedIn) {
            val periodicConstraints: Constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val periodicWorker = PeriodicWorkRequestBuilder<RefreshDataWorker>(12, TimeUnit.HOURS)
                .addTag("PeriodicWorker")
                .setConstraints(periodicConstraints)
                .setInitialDelay(1,TimeUnit.MINUTES)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()

            WorkManager.getInstance(this).enqueue(periodicWorker)
        }
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
