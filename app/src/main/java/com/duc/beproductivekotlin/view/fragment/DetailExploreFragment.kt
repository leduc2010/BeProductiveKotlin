package com.duc.beproductivekotlin.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.duc.beproductivekotlin.databinding.DetailExploreFragmentBinding
import com.duc.beproductivekotlin.viewmodel.BaseViewModel

class DetailExploreFragment : BaseFragment<DetailExploreFragmentBinding, BaseViewModel>() {
    override fun getClassViewModel(): Class<BaseViewModel> {
        return BaseViewModel::class.java
    }

    override fun initView() {
        val exploreItem = DetailExploreFragmentArgs.fromBundle(requireArguments()).exploreItem
        val assetPath = "file:///android_asset/explore/${exploreItem.image}.jpg"
        Glide.with(requireContext()).load(assetPath).into(binding.ivImage)
        binding.tvTitle.text = exploreItem.title
        binding.tvAuthor.text = exploreItem.author
        binding.tvContent.text = exploreItem.content

        binding.ivBack.setOnClickListener { navController.popBackStack() }
    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DetailExploreFragmentBinding {
        return DetailExploreFragmentBinding.inflate(inflater, container, false)
    }
}