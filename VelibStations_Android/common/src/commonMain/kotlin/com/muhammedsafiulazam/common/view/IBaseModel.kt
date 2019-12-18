package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.IAddOn

interface IBaseModel : IAddOn {
    fun onLoad()
    fun onUnload()
}