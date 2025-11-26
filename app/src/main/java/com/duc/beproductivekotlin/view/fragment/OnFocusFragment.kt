package com.duc.beproductivekotlin.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.CountDownTimer
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.duc.beproductivekotlin.CommonUtils
import com.duc.beproductivekotlin.MediaManager
import com.duc.beproductivekotlin.R
import com.duc.beproductivekotlin.databinding.OnFocusFragmentBinding
import com.duc.beproductivekotlin.service.ThemeSongService
import com.duc.beproductivekotlin.viewmodel.OnFocusVM

class OnFocusFragment : BaseFragment<OnFocusFragmentBinding, OnFocusVM>() {
    override fun getClassViewModel(): Class<OnFocusVM> {
        return OnFocusVM::class.java
    }

    private val requestNotificationPermissionLauncher = registerForActivityResult<String, Boolean>(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startFocus()
        }
    }

    override fun initView() {
        val start = OnFocusFragmentArgs.fromBundle(requireArguments()).startTimer
        viewModel.pokemon = OnFocusFragmentArgs.fromBundle(requireArguments()).pokemon
        if (start) {
            startFocus()
        }
        viewModel.currentTheme.observe(
            getViewLifecycleOwner(), Observer { themeFile: String? -> this.loadTheme(themeFile!!) })
        binding.ivBack.setOnClickListener { viewModel.previousTheme() }
        binding.ivNext.setOnClickListener { viewModel.nextTheme() }
        viewModel.setThemeFiles(loadThemeFile())
        binding.btStart.setOnClickListener { startFocus() }
        binding.btExit.setOnClickListener { navController.popBackStack() }
        binding.ivSpeaker.setOnClickListener { this.mute() }
        binding.btGift.setOnClickListener { takeGift(viewModel.pokemon) }
        binding.btCollection.setOnClickListener { moveToCollection() }
    }

    private fun moveToCollection() {
        navController.navigate(OnFocusFragmentDirections.actionOnFocusFragmentToCollectionFragment())
    }

    private fun takeGift(pokemon: String?) {
        binding.lnCongrat.visibility = View.GONE
        initPokemon(pokemon)
        Log.d("pokemon", viewModel.pokemon.toString())
        binding.lnPokemon.visibility = View.VISIBLE
    }

    private fun initPokemon(pokemon: String?) {
        Glide.with(requireContext()).load("file:///android_asset/theme/pokemon/$pokemon")
            .into(binding.ivPokemon)
        binding.tvPokemonName.text = pokemon?.dropLast(5)
    }

    private fun mute() {
        val currentLevel = binding.ivSpeaker.getDrawable().level
        val newLevel = if (currentLevel == 0) 1 else 0
        binding.ivSpeaker.setImageLevel(newLevel)
        if (newLevel == 1) {
            MediaManager.instance?.setVolume(0f)
        } else {
            MediaManager.instance?.setVolume(1f)
        }
    }

    private fun startFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                return
            }
        }

        if (!Settings.canDrawOverlays(requireContext())) {
            Toast.makeText(
                requireContext(), "Cần cấp quyền hiển thị trên ứng dụng khác", Toast.LENGTH_LONG
            ).show()
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(intent)
            return
        }
        binding.btStart.visibility = View.GONE
        binding.btExit.visibility = View.VISIBLE
        binding.trThemeName.visibility = View.GONE
        binding.frCountDown.visibility = View.VISIBLE

        startFocusService()
        startCountDown()
        requireActivity().startLockTask()
    }

    private fun stopFocusMode() {
        requireActivity().stopLockTask()
        requireActivity().stopService(Intent(requireContext(), ThemeSongService::class.java))
        binding.btExit.visibility = View.GONE
        binding.lnCongrat.visibility = View.VISIBLE
        binding.ivSpeaker.visibility = View.GONE
        binding.ivClose.visibility = View.VISIBLE
        binding.ivClose.setOnClickListener { navController.popBackStack() }
    }

    private fun startFocusService() {
        val intent = Intent(context, ThemeSongService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().startForegroundService(intent)
        }
    }

    private fun loadThemeFile(): Array<String>? {
        try {
            return resources.assets.list("theme/background")
        } catch (e: Exception) {
            e.printStackTrace()
            return arrayOfNulls<String>(0) as Array<String>?
        }
    }

    private fun loadTheme(themeFile: String) {
        val themeName = themeFile.dropLast(5)
        binding.tvThemeName.text = themeName
        Glide.with(requireContext()).load("file:///android_asset/theme/background/$themeFile")
            .into(binding.ivTheme)
    }

    private fun startCountDown() {
        object : CountDownTimer(10000, 1000) {
            override fun onFinish() {
                if (!isAdded) return
                binding.tvCountDown.setText(R.string.txt_giai_lao)
                requireActivity().stopService(Intent(context, ThemeSongService::class.java))
                stopFocusMode()
                saveToCollection()
            }

            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000 / 60).toInt()
                val seconds = (millisUntilFinished / 1000 % 60).toInt()
                @SuppressLint("DefaultLocale") val time =
                    String.format("%02d:%02d", minutes, seconds)
                binding.tvCountDown.text = time
            }
        }.start()
    }

    private fun saveToCollection() {
        val oldList = CommonUtils.instance?.getPref(POKEMON_COLLECTION)
        val newSaved = if (oldList.isNullOrEmpty()) {
            viewModel.pokemon
        } else {
            "$oldList,${viewModel.pokemon}"
        }
        CommonUtils.instance?.savePref(POKEMON_COLLECTION, newSaved)
    }

    override fun initViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): OnFocusFragmentBinding {
        return OnFocusFragmentBinding.inflate(inflater, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(requireContext(), ThemeSongService::class.java)
        requireActivity().stopService(intent)
    }

    companion object {
        const val POKEMON_COLLECTION = "POKEMON_COLLECTION"
    }
}
