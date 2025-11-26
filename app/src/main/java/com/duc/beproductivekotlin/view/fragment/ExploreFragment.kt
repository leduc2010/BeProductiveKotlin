package com.duc.beproductivekotlin.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.duc.beproductivekotlin.adapter.NewArticleAdapter
import com.duc.beproductivekotlin.adapter.PopularAdapter
import com.duc.beproductivekotlin.databinding.ExploreFragmentBinding
import com.duc.beproductivekotlin.viewmodel.ExploreViewModel

@Suppress("UNCHECKED_CAST")
class ExploreFragment : BaseFragment<ExploreFragmentBinding, ExploreViewModel>() {
    private var popularAdpt: PopularAdapter? = null
    private var newArticleAdpt: NewArticleAdapter? = null
    override fun getClassViewModel(): Class<ExploreViewModel> {
        return ExploreViewModel::class.java
    }

    override fun initView() {
        popularAdpt = PopularAdapter(
            viewModel.listPopular, requireContext()
        ) { exploreItem ->
            val action =
                ExploreFragmentDirections.actionExploreFragmentToDetailExploreFragment(exploreItem)
            navController.navigate(action)
        }
        newArticleAdpt = NewArticleAdapter(
            viewModel.listNewArticle, requireContext()
        ) { exploreItem ->
            val action =
                ExploreFragmentDirections.actionExploreFragmentToDetailExploreFragment(exploreItem)
            navController.navigate(action)
        }
        binding.rvPopular.adapter = popularAdpt
        binding.vpNewArticle.adapter = newArticleAdpt
        binding.dotsIndicator.attachTo(binding.vpNewArticle)

    }

    override fun initViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): ExploreFragmentBinding {
        return ExploreFragmentBinding.inflate(inflater, container, false)
    }
}