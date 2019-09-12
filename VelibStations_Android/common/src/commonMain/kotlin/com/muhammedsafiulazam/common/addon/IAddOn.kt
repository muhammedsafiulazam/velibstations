package com.muhammedsafiulazam.common.addon

interface IAddOn {
    /**
    * Get addon.
    * @param type type of addon
    * @return addon for type
    */
    fun getAddOn(type: String) : IAddOn?

    /**
     * Get addons.
     * @return map of addons
     */
    fun getAddOns() : Map<String, IAddOn>

    /**
     * Add addon.
     * @param type type of addon
     * @param addOn addon to be added
     */
    fun addAddOn(type: String, addOn: IAddOn)

    /**
     * Add addons.
     * @param addons map of addons
     */
    fun addAddOns(addons: Map<String, IAddOn>)

    /**
     * Remove addon.
     * @param type type of addon
     */
    fun removeAddOn(type: String)

    /**
     * Remove addons.
     * @param types types of addons
     */
    fun removeAddOns(types: List<String>)

    /**
     * Clear addons.
     */
    fun clearAddOns()
}