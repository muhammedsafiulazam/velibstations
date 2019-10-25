package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.IAddOn

actual interface IBaseViewModel : IAddOn {
    actual fun getView() : IBaseView?
    actual fun setView(view: IBaseView?)

    fun onViewLoad()
    fun onViewUnload()
}