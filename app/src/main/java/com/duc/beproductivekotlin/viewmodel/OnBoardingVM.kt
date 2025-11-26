package com.duc.beproductivekotlin.viewmodel

import com.duc.beproductivekotlin.R
import com.duc.beproductivekotlin.model.Onboarding

class OnBoardingVM : BaseViewModel() {
    val listOnboarding: MutableList<Onboarding?>
        get() {
            val list: MutableList<Onboarding?> =
                ArrayList<Onboarding?>()
            list.add(Onboarding(R.string.txt_welcome, R.mipmap.ic_onboarding1, R.string.none))
            list.add(
                Onboarding(
                    R.string.txt_be_focused,
                    R.mipmap.ic_onboarding2,
                    R.string.txt_desc_onboarding2
                )
            )
            list.add(
                Onboarding(
                    R.string.txt_note_taking,
                    R.mipmap.ic_onboarding3,
                    R.string.txt_desc_onboarding3
                )
            )
            list.add(
                Onboarding(
                    R.string.txt_explore,
                    R.mipmap.ic_onboarding4,
                    R.string.txt_desc_onboarding4
                )
            )
            return list
        }
}
