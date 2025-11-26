package com.duc.beproductivekotlin.view.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.duc.beproductivekotlin.CommonUtils
import com.duc.beproductivekotlin.R
import com.duc.beproductivekotlin.adapter.OnboardingAdapter
import com.duc.beproductivekotlin.databinding.OnboardingFragmentBinding
import com.duc.beproductivekotlin.model.Onboarding
import com.duc.beproductivekotlin.viewmodel.OnBoardingVM

@Suppress("UNCHECKED_CAST")
class OnboardingFragment : BaseFragment<OnboardingFragmentBinding, OnBoardingVM>() {
    private var adapter: OnboardingAdapter? = null

    override fun getClassViewModel(): Class<OnBoardingVM> {
        return OnBoardingVM::class.java
    }

    override fun initView() {
        adapter = OnboardingAdapter(requireContext(), viewModel.listOnboarding as MutableList<Onboarding>)
        binding.vpOnboarding.setAdapter(adapter)
        binding.dotsIndicator.attachTo(binding.vpOnboarding)
        CommonUtils.instance?.savePref("first_launch", "false")

        binding.btNext.setOnClickListener { v: View? ->
            val navController = NavHostFragment.findNavController(this)
            navController.navigate(R.id.action_onBoardingFragment_to_homeFragment)
        }
    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): OnboardingFragmentBinding {
        return OnboardingFragmentBinding.inflate(inflater, container, false)
    }
}
