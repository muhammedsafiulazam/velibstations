package com.muhammedsafiulazam.common.view

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

actual interface IViewManager : IBaseViewManager {
    fun loadView(view: String?)
    fun loadView(view: String?, data: Any?)
}
