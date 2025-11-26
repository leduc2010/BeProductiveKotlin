package com.duc.beproductivekotlin.view.fragment

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.NavOptions
import com.duc.beproductivekotlin.CommonUtils
import com.duc.beproductivekotlin.R
import com.duc.beproductivekotlin.databinding.SplashFragmentBinding
import com.duc.beproductivekotlin.viewmodel.BaseViewModel

class SplashFragment : BaseFragment<SplashFragmentBinding, BaseViewModel>() {
    override fun getClassViewModel(): Class<BaseViewModel> {
        return BaseViewModel::class.java
    }

    override fun initView() {
        binding.ivLogo.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_in_fade_in)
        )
        Handler().postDelayed({
            val firstLaunch = CommonUtils.instance?.getPref("first_launch") == null
            if (firstLaunch) {
                navController.navigate(R.id.action_splashFragment_to_onBoardingFragment)
            } else {
                navController.navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }, 2000)
    }

    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): SplashFragmentBinding {
        return SplashFragmentBinding.inflate(inflater, container, false)
    }
}
