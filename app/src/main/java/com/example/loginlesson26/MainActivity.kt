package com.example.loginlesson26


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
import com.example.loginlesson26.databinding.ActivityMainBinding
import com.example.loginlesson26.presentation.LoginFragment
import com.example.loginlesson26.presentation.TrackListFragment
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val preferences by lazy {
        CustomPreference(applicationContext)
    }
    private val loginManager by lazy {
        LoginManager(preferences)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setSupportActionBar(binding.toolbar)


        if (savedInstanceState == null) {
            if (preferences.login!==""&&preferences.password!=="") {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, TrackListFragment.newInstance())
                    .commit()
            } else {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, LoginFragment.newInstance())
                    .commit()
            }
        }


            val periodicConstraints: Constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()


            val periodicWorker = PeriodicWorkRequestBuilder<RefreshDataWorker>(MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                .addTag("PeriodicWorker")
                .setInitialDelay(1,TimeUnit.MINUTES)
                .setConstraints(periodicConstraints)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()

            WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(RefreshDataWorker.WORK_NAME,ExistingPeriodicWorkPolicy.KEEP,periodicWorker)


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
        loginManager.logout()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, LoginFragment.newInstance())
            .commit()
    }

}
