package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.IAddOn
import kotlin.reflect.KClass

interface IBaseView : IAddOn {
    fun getData() : Any?
    fun getViewModel() : IBaseViewModel?
    fun setViewModel(viewModel: String)

    fun onViewLoad()
    fun onViewStart()
    fun onViewResume()
    fun onViewPause()
    fun onViewStop()
    fun onViewUnload()
}