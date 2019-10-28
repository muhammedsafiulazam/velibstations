package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.IAddOn

expect interface IBaseView : IAddOn {
    fun getData() : Any?
    fun setData(data: Any?)
    fun getViewModel() : IBaseViewModel?
    fun setViewModel(viewModel: String)
}