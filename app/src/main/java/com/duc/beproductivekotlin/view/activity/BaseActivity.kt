package com.duc.beproductivekotlin.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.R
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding, V : ViewModel> : AppCompatActivity(),
    View.OnClickListener {
    protected lateinit var viewModel: V
    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()

        binding = initViewBinding()
        viewModel = ViewModelProvider(this)[initViewModel()]
        setContentView(binding.root)
        initView()
    }

    protected abstract fun initView()

    protected abstract fun initViewModel(): Class<V>

    protected abstract fun initViewBinding(): B

    @SuppressLint("PrivateResource")
    override fun onClick(v: View) {
        v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_fade_in))
        clickView(v)
    }

    protected open fun clickView(v: View) {

    }
}