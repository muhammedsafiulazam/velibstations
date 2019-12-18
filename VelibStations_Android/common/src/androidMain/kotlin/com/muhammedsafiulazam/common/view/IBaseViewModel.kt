package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.IAddOn

actual interface IBaseViewModel : IAddOn {
    actual fun getModel(): IBaseModel?
    actual fun setModel(model: String)
    actual fun onLoad()
    actual fun onUnload()
}