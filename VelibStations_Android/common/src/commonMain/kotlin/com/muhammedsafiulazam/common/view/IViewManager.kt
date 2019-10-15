package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.IAddOn
import kotlin.reflect.KClass

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IViewManager : IAddOn {
    fun getCurrentView() : IBaseView?
    fun onChangeView(view: IBaseView?)

    fun loadView(view: String?, info: Any?, data: Any?)

    // Temporary data management.
    fun pop(identifier: String) : Any?
    fun push(identifier: String, data: Any?)
}