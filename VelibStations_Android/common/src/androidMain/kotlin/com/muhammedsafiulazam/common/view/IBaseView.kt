package com.muhammedsafiulazam.common.view

import android.os.Bundle
import com.muhammedsafiulazam.common.addon.IAddOn
import kotlin.reflect.KClass

actual interface IBaseView : IAddOn {
    actual fun getData() : Any?
    actual fun setData(data: Any?)
    actual fun getViewModel() : IBaseViewModel?
    actual fun setViewModel(viewModel: String)

    fun onViewLoad(state: Bundle?)
    fun onViewSave(state: Bundle?)
    fun onViewStart()
    fun onViewResume()
    fun onViewPause()
    fun onViewStop()
    fun onViewUnload()
}