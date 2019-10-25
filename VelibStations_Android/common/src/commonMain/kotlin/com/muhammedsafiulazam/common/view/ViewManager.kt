package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.AddOn
import com.muhammedsafiulazam.common.utils.ViewUtils

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

expect class ViewManager : BaseViewManager {
}

open class BaseViewManager : AddOn(), IViewManager {
    override fun getCurrentView() : IBaseView? {
        return null
    }

    override fun loadView(view: String?, data: Any?) {
        loadView(view, null, null, data)
    }

    override fun loadView(view: String?, story: String?, modal: Boolean?, data: Any?) {
        ViewUtils.loadView(view, story, modal, data)
    }
}