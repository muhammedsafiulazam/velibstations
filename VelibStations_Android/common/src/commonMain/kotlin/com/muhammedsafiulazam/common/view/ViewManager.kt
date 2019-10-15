package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.AddOn
import kotlin.reflect.KClass

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class ViewManager : AddOn(), IViewManager {
    private var mCurrentView: IBaseView? = null
    private var mData: MutableMap<String, Any> = mutableMapOf()
    var loadViewMechanism: ((view: String?, info: Any?, data: Any?) -> IBaseViewModel)? = null

    override fun getCurrentView() : IBaseView? {
        return mCurrentView
    }

    override fun onChangeView(view: IBaseView?) {
        mCurrentView = view
    }

    override fun loadView(view: String?, info: Any?, data: Any?) {
        if (loadViewMechanism == null) {
            error("ViewManager needs loading view mechanism.")
        } else {
            loadViewMechanism?.let { it(view, info, data) }
        }
    }

    override fun pop(identifier: String) : Any? {
        val data: Any? = mData.get(identifier)
        mData.remove(identifier)
        return data
    }

    override fun push(identifier: String, data: Any?) {
        if (data != null) {
            mData.put(identifier, data)
        }
    }
}