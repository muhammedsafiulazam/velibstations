package com.muhammedsafiulazam.common.utils

import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.view.IBaseView
import com.muhammedsafiulazam.common.view.IViewManager
import platform.UIKit.UIApplication
import platform.UIKit.UIStoryboard
import platform.UIKit.UIViewController

actual object ViewUtils {
    actual fun loadView(view: String?, story: String?, info: Any?, data: Any?) {
        val viewController = UIStoryboard.storyboardWithName(story!!, null).instantiateViewControllerWithIdentifier(view!!) as? UIViewController
        if (viewController != null) {
            (viewController as? IBaseView)?.setData(data)
            val viewManager: IViewManager? = AddOnManager.getAddOn(AddOnType.VIEW_MANAGEER) as IViewManager
            val currentViewController: UIViewController? = viewManager?.getCurrentView() as? UIViewController
            if (currentViewController != null && info != null && !(info as Boolean)) {
                currentViewController.presentModalViewController(viewController, true)
            } else {
                UIApplication.sharedApplication.delegate?.window?.rootViewController = viewController
                UIApplication.sharedApplication.delegate?.window?.makeKeyAndVisible()
            }
        }
    }
}