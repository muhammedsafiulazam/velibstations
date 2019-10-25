package com.muhammedsafiulazam.common.utils

import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.view.IBaseView
import com.muhammedsafiulazam.common.view.IViewManager
import platform.Foundation.setValue
import platform.UIKit.UIApplication
import platform.UIKit.UIStoryboard
import platform.UIKit.UIViewController

actual object ViewUtils {
    actual fun loadView(view: String?, story: String?, modal: Boolean?, data: Any?) {
        val viewManager: IViewManager? = AddOnManager.getAddOn(AddOnType.VIEW_MANAGEER) as? IViewManager
        val viewController = UIStoryboard.storyboardWithName(story!!, null).instantiateViewControllerWithIdentifier(view!!) as? UIViewController
        if (viewController != null) {
            (viewController as? IBaseView)?.setData(data)
            val currentViewController: UIViewController? = viewManager?.getCurrentView() as? UIViewController
            if (currentViewController != null && modal != null && !(modal as Boolean)) {
                currentViewController.presentModalViewController(viewController, true)
            } else {
                UIApplication.sharedApplication.delegate?.window?.rootViewController = viewController
                UIApplication.sharedApplication.delegate?.window?.makeKeyAndVisible()
            }
        }
    }
}