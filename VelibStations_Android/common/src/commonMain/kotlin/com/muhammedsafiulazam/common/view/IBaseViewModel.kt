package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.IAddOn

expect interface IBaseViewModel : IAddOn {
    fun getModel(): IBaseModel?
    fun setModel(model: String)
    fun onLoad()
    fun onUnload()
}