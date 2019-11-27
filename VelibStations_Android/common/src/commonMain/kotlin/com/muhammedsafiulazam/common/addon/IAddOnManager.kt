package com.muhammedsafiulazam.common.addon

interface IAddOnManager : IAddOn {
    /**
     * Initialize with context.
     * @param context context
     */
    fun initialize(context: Any)
}