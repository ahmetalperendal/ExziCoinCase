package com.exzi.coincase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.exzi.coincase.databinding.ActivityMainBinding
import com.exzi.coincase.repository.PreferencesRepository
import com.exzi.coincase.utils.GlobalHelper
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        // bu satır, uygulamanın her zaman LIGHT temada çalışmasını sağlar
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navGraph =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navGraph.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    fun showProgressBar() {
        binding.apply {
            llLoading.visibility = View.VISIBLE
            GlobalHelper.setTouchable(this@MainActivity, false)
            animationLoading.playAnimation()
        }
    }

    fun hideProgressBar() {
        binding.apply {
            llLoading.visibility = View.GONE
            GlobalHelper.setTouchable(this@MainActivity, true)
            animationLoading.pauseAnimation()
        }
    }
}
