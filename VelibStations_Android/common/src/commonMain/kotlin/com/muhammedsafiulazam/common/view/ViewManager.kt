package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.AddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

expect class ViewManager() : BaseViewManager {
}

open class BaseViewManager : AddOn(), IBaseViewManager {
    override fun getCurrentView() : IBaseView? {
        return null
    }
}