package com.duc.beproductivekotlin.view.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import com.bumptech.glide.Glide
import com.duc.beproductivekotlin.CommonUtils
import com.duc.beproductivekotlin.databinding.FocusFragmentBinding
import com.duc.beproductivekotlin.view.fragment.OnFocusFragment.Companion.POKEMON_COLLECTION
import com.duc.beproductivekotlin.viewmodel.FocusViewModel
import java.io.IOException

class FocusFragment : BaseFragment<FocusFragmentBinding, FocusViewModel>() {
    override fun getClassViewModel(): Class<FocusViewModel> {
        return FocusViewModel::class.java
    }

    override fun initView() {
        loadPokemon()
        binding.btStart.setOnClickListener { startOnFocust() }
        binding.ivOption.setOnClickListener { showOnFocustTheme() }
        binding.edtFocus.setOnFocusChangeListener { v: View?, hasFocus: Boolean ->
            if (hasFocus) {
                binding.trFocus.visibility = View.GONE
            } else {
                binding.trFocus.visibility = View.VISIBLE
            }
        }
    }

    private fun showOnFocustTheme() {
        val action: NavDirections =
            FocusFragmentDirections.actionFocusFragmentToOnFocusFragment(
                false,
                "",
                viewModel.currentPokemon
            )
        navController.navigate(action)
    }

    private fun startOnFocust() {
        val text = binding.edtFocus.getText().toString()
        val action: NavDirections =
            FocusFragmentDirections.actionFocusFragmentToOnFocusFragment(
                true,
                text,
                viewModel.currentPokemon
            )
        navController.navigate(action)
    }

    private fun loadPokemon() {
        val assetManager = requireContext().assets
        val folderPath = "theme/pokemon"
        try {
            val files = assetManager.list(folderPath)
            for (file in files!!) {
                if (CommonUtils.instance?.getPref(POKEMON_COLLECTION)?.contains(file) == false) {
                    Glide.with(requireContext())
                        .load("file:///android_asset/$folderPath/$file")
                        .into(binding.ivPokemon)
                    viewModel.currentPokemon = file
                    break
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FocusFragmentBinding {
        return FocusFragmentBinding.inflate(inflater, container, false)
    }
}
