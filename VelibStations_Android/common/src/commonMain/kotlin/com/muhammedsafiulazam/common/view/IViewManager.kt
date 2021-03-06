package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.IAddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

expect interface IViewManager : IBaseViewManager {
}

interface IBaseViewManager : IAddOn {
    fun getCurrentView() : IBaseView?
}
