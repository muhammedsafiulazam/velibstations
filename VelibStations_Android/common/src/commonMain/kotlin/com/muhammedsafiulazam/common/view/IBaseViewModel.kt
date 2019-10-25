package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.IAddOn

expect interface IBaseViewModel : IAddOn {
    fun getView() : IBaseView?
    fun setView(view: IBaseView?)
}