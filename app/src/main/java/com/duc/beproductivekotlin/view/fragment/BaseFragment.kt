package com.duc.beproductivekotlin.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.duc.beproductivekotlin.App
import com.duc.beproductivekotlin.Storage
import com.duc.beproductivekotlin.viewmodel.BaseViewModel

abstract class BaseFragment<B : ViewBinding, V : BaseViewModel> : Fragment(), View.OnClickListener {

    protected lateinit var binding: B
    protected lateinit var viewModel: V
    protected var data: Any? = null
    protected lateinit var navController: androidx.navigation.NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = initViewBinding(inflater, container)
        viewModel = ViewModelProvider(this)[getClassViewModel()]
        initView()
        navController = NavHostFragment.findNavController(this)
        return binding.root
    }

    protected abstract fun getClassViewModel(): Class<V>

    @SuppressLint("PrivateResource")
    override fun onClick(view: View) {
        view.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in))
        clickView(view)
    }

    protected open fun clickView(view: View) {}

    protected abstract fun initView()

    protected abstract fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): B
}
