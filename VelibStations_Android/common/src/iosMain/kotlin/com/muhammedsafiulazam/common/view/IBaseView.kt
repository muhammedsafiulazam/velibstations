package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.IAddOn

actual interface IBaseView : IAddOn {
    actual fun getData() : Any?
    actual fun setData(data: Any?)
    actual fun getViewModel() : IBaseViewModel?
    actual fun setViewModel(viewModel: String)
}