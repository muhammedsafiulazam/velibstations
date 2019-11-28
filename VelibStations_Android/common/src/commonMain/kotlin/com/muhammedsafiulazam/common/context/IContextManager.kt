package com.muhammedsafiulazam.photoalbum.context

import com.muhammedsafiulazam.common.addon.IAddOn


interface IContextManager : IAddOn {
    /**
     * Get context.
     * @param context context
     */
    fun getContext() : Any
}