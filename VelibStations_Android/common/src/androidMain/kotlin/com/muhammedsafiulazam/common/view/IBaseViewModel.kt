package com.muhammedsafiulazam.common.view

import android.os.Bundle
import com.muhammedsafiulazam.common.addon.IAddOn

actual interface IBaseViewModel : IAddOn {
    actual fun getView() : IBaseView?
    actual fun setView(view: IBaseView?)

    fun onViewLoad()
    fun onViewStart()
    fun onViewResume()
    fun onViewPause()
    fun onViewStop()
    fun onViewUnload()
}