package com.duc.beproductivekotlin.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.duc.beproductivekotlin.CommonUtils
import com.duc.beproductivekotlin.adapter.CollectionAdapter
import com.duc.beproductivekotlin.databinding.CollectionFragmentBinding
import com.duc.beproductivekotlin.view.fragment.ProfileFragment.Companion.PHOTO_PROFILE
import com.duc.beproductivekotlin.viewmodel.CollectionVM

class CollectionFragment : BaseFragment<CollectionFragmentBinding, CollectionVM>() {

    lateinit var adapter: CollectionAdapter

    override fun getClassViewModel(): Class<CollectionVM> {
        return CollectionVM::class.java
    }

    override fun initView() {
        initAvatar()
        binding.ivBack.setOnClickListener { navController.popBackStack() }
        adapter = CollectionAdapter(
            requireContext(),
            List(12) { "" },
            List(12) { false }
        )
        binding.rvCollection.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvCollection.adapter = adapter
        viewModel.pokemonList.observe(viewLifecycleOwner) { list ->
            adapter.updatePokemonList(list)
        }
        viewModel.unlockedList.observe(viewLifecycleOwner) { list ->
            adapter.updateUnlockedList(list)
        }
    }

    private fun initAvatar() {
        CommonUtils.instance?.getPref(PHOTO_PROFILE)?.let { savedUri ->
            Glide.with(requireContext())
                .load(savedUri.toUri())
                .circleCrop()
                .into(binding.ivAvatar)
        }
    }

    override fun initViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): CollectionFragmentBinding {
        return CollectionFragmentBinding.inflate(inflater, container, false)
    }
}

