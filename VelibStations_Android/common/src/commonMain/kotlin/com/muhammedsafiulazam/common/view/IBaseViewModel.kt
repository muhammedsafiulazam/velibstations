package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.IAddOn

interface IBaseViewModel : IAddOn {
    fun getView() : IBaseView?
    fun setView(view: IBaseView?)

    fun onViewLoad()
    fun onViewStart()
    fun onViewResume()
    fun onViewPause()
    fun onViewStop()
    fun onViewUnload()
}