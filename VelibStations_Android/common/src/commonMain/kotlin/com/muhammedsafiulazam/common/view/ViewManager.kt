package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.AddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class ViewManager : AddOn(), IViewManager {
    private var mCurrentView: IBaseView? = null
    private var mData: MutableMap<String, Any> = mutableMapOf()
    private var loadViewMechanism: ((view: String?, story: String?, info: Any?, data: Any?) -> Unit)? = null

    // Index.
    private var mIndex: Int = 0

    override fun getCurrentView() : IBaseView? {
        return mCurrentView
    }

    override fun onChangeView(view: IBaseView?) {
        mCurrentView = view
    }

    override fun loadView(view: String?, data: Any?) {
        loadView(view, null, null, data)
    }

    override fun loadView(view: String?, story: String?, info: Any?, data: Any?) {
        if (loadViewMechanism == null) {
            error("ViewManager needs load view mechanism.")
        } else {
            loadViewMechanism?.let { it(view, story, info, data) }
        }
    }

    override fun loadViewMechanism(mechanism: ((view: String?, story: String?, info: Any?, data: Any?) -> Unit)?) {
        loadViewMechanism = mechanism
    }

    override fun pop(identifier: String?) : Any? {
        var data: Any? = null
        if (identifier != null) {
            data = mData.get(identifier)
            mData.remove(identifier)
        }
        return data
    }

    override fun push(data: Any?) : String? {
        var identifier: String? = null
        if (data != null) {
            mIndex += 1
            identifier = "" + mIndex
            mData.put(identifier, data)
        }
        return identifier
    }
}