package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.IAddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IViewManager : IAddOn {
    fun getCurrentView() : IBaseView?
    fun onChangeView(view: IBaseView?)

    fun loadView(view: String?, data: Any?)
    fun loadView(view: String?, story: String?, info: Any?, data: Any?)
    fun loadViewMechanism(mechanism: ((view: String?, story: String?, info: Any?, data: Any?) -> Unit)?)

    // Temporary data management.
    fun pop(identifier: String?) : Any?
    fun push(data: Any?) : String?
}