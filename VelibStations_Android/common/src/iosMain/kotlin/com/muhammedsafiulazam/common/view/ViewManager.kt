package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import platform.UIKit.UIApplication
import platform.UIKit.UIStoryboard
import platform.UIKit.UIViewController

actual class ViewManager : BaseViewManager(), IViewManager {
    override fun getCurrentView() : IBaseView? {
        return UIApplication.sharedApplication.keyWindow?.rootViewController as? IBaseView
    }

    override fun loadView(view: String?, story: String?) {
        loadView(view, story, null, null)
    }

    override fun loadView(view: String?, story: String?, data: Any?) {
        loadView(view, story, null, data)
    }

    override fun loadView(view: String?, story: String?, modal: Boolean?) {
        loadView(view, story, modal, null)
    }

    override fun loadView(view: String?, story: String?, modal: Boolean?, data: Any?) {
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