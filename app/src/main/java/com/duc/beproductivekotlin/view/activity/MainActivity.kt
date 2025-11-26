package com.duc.beproductivekotlin.view.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.duc.beproductivekotlin.CommonUtils
import com.duc.beproductivekotlin.R
import com.duc.beproductivekotlin.databinding.ActivityMainBinding
import com.duc.beproductivekotlin.viewmodel.BaseViewModel

class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {
    override fun initView() {
        applySavedTheme()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment?

        if (navHostFragment != null) {
            val navController = navHostFragment.navController
            setupWithNavController(binding.bottomNav, navController)
            navController.addOnDestinationChangedListener { controller: NavController?, destination: NavDestination?, arguments: Bundle? ->
                if (destination!!.id == R.id.splashFragment
                    || destination.id == R.id.onboardingFragment
                    || destination.id == R.id.addNoteFragment
                    || destination.id == R.id.noteDetailFragment
                    || destination.id == R.id.detailExploreFragment
                    || destination.id == R.id.collectionFragment
                    || destination.id == R.id.onFocusFragment) {
                    binding.bottomNav.visibility = View.GONE
                } else {
                    binding.bottomNav.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun applySavedTheme() {
        val themePref = CommonUtils.instance?.getPref("theme_app")
        val isDark = themePref == "dark"

        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    override fun initViewModel(): Class<BaseViewModel> {
        return BaseViewModel::class.java
    }

    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}