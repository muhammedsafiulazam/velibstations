package com.muhammedsafiulazam.common.view

import android.app.Activity
import android.content.Intent
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType

actual class ViewManager : BaseViewManager(), IViewManager {
    companion object {
        private val TAG: String = "VIEW_MANAGER_"
        val KEY_DATA_IDENTIFIER: String = TAG + "KEY_DATA_IDENTIFIER"
    }

    private var mIndex: Int = 0
    private var mData: MutableMap<String, Any> = mutableMapOf()
    private var mCurrentView: IBaseView? = null

    override fun getCurrentView() : IBaseView? {
        return mCurrentView
    }

    fun onViewActive(view: IBaseView?) {
        mCurrentView = view
    }

    fun onViewDeactive(view: IBaseView?) {
        if (view == mCurrentView) {
            mCurrentView = null
        }
    }

    override fun loadView(view: String?) {
        loadView(view, null)
    }

    override fun loadView(view: String?, data: Any?) {
        val viewManager: ViewManager? = AddOnManager.getAddOn(AddOnType.VIEW_MANAGEER) as? ViewManager
        val activity: Activity? = viewManager?.getCurrentView() as? Activity
        if (activity != null) {
            val intent = Intent(activity, Class.forName(view))
            if (data != null) {
                val identifier = viewManager?.push(data)
                intent.putExtra(KEY_DATA_IDENTIFIER, identifier)
            }
            activity?.startActivity(intent)
        }
    }

    // Temporary data management.
    fun pop(identifier: String?) : Any? {
        var data: Any? = null
        if (identifier != null) {
            data = mData.get(identifier)
            mData.remove(identifier)
        }
        return data
    }

    fun push(data: Any?) : String? {
        var identifier: String? = null
        if (data != null) {
            mIndex += 1
            identifier = "" + mIndex
            mData.put(identifier, data)
        }
        return identifier
    }
}