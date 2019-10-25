package com.muhammedsafiulazam.common.view

import platform.UIKit.UIApplication

actual class ViewManager : BaseViewManager(), IViewManager {
    override fun getCurrentView() : IBaseView? {
        return UIApplication.sharedApplication.keyWindow?.rootViewController as? IBaseView
    }
}