package com.duc.beproductivekotlin.view.fragment

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Switch
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.duc.beproductivekotlin.CommonUtils
import com.duc.beproductivekotlin.R
import com.duc.beproductivekotlin.databinding.ProfileFragmentBinding
import com.duc.beproductivekotlin.view.dialog.ProfileDialog
import com.duc.beproductivekotlin.viewmodel.ProfileVM

class ProfileFragment : BaseFragment<ProfileFragmentBinding, ProfileVM>() {
    private var profileDialog: ProfileDialog? = null

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            profileDialog?.setPhoto(uri)
        }
    }

    override fun getClassViewModel(): Class<ProfileVM> {
        return ProfileVM::class.java
    }

    override fun initView() {
        initTheme()
        initPhoto()
        initInfo()
        binding.trCollection.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_collectionFragment)
        }
        binding.ivPhoto.setOnClickListener { initDialog() }
    }

    private fun initInfo() {
        CommonUtils.instance?.getPref("name")?.let { name ->
            binding.tvName.text = name
        }
        CommonUtils.instance?.getPref("email")?.let { email ->
            binding.tvEmail.text = email
        }
    }

    private fun initPhoto() {
        CommonUtils.instance?.getPref(PHOTO_PROFILE)?.let { savedUri ->
            Glide.with(requireContext())
                .load(savedUri.toUri())
                .circleCrop()
                .into(binding.ivPhoto)
        }
    }

    private fun initDialog() {
        profileDialog = ProfileDialog(requireContext(), object : ProfileDialog.OnDeleteListener {
            override fun onSave(name: String, email: String, photoUri: Uri?) {
                photoUri?.let {
                    CommonUtils.instance?.savePref(PHOTO_PROFILE, it.toString())
                    Glide.with(requireContext()).load(photoUri).circleCrop()
                        .into(binding.ivPhoto)
                }
                CommonUtils.instance?.savePref("name", name)
                CommonUtils.instance?.savePref("email", email)
                binding.tvName.text = name
                binding.tvEmail.text = email
            }

            override fun onChosePhoto() {
                pickImageLauncher.launch("image/*")
            }
        })
        profileDialog?.show()
    }

    private fun initTheme() {

        val theme = CommonUtils.instance?.getPref("theme_app")
        val isDark  = if(theme.equals("dark")) true else false
        binding.switchTheme.isChecked = isDark

        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            CommonUtils.instance?.savePref("theme_app", if(isChecked) "dark" else "light")
        }
    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ProfileFragmentBinding {
        return ProfileFragmentBinding.inflate(inflater, container, false)
    }

    companion object {
        const val PHOTO_PROFILE = "PHOTO_PROFILE"
    }
}