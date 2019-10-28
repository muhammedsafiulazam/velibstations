package com.muhammedsafiulazam.common.view

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

actual interface IViewManager : IBaseViewManager {
    fun loadView(view: String?, story: String?)
    fun loadView(view: String?, story: String?, data: Any?)
    fun loadView(view: String?, story: String?, modal: Boolean?)
    fun loadView(view: String?, story: String?, modal: Boolean?, data: Any?)
}
