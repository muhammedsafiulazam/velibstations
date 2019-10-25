package com.muhammedsafiulazam.common.view

actual class ViewManager : BaseViewManager() {
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