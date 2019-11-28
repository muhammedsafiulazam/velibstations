package com.muhammedsafiulazam.photoalbum.context

import com.muhammedsafiulazam.common.addon.AddOn

class ContextManager(context: Any) : AddOn(), IContextManager {

    private var mContext: Any

    init {
        mContext = context
    }

    /**
     * Get context.
     * @param context context
     */
    override fun getContext() : Any {
        return mContext
    }
}