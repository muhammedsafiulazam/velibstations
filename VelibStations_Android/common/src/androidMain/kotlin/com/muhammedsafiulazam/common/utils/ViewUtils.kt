package com.muhammedsafiulazam.common.utils

import android.app.Activity
import android.content.Intent
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.view.IViewManager

actual object ViewUtils {
    const val KEY_DATA_IDENTIFIER: String = "KEY_DATA_IDENTIFIER"
    actual fun loadView(view: String?, story: String?, modal: Boolean?, data: Any?) {
        val viewManager: IViewManager? = AddOnManager.getAddOn(AddOnType.VIEW_MANAGEER) as IViewManager
        val activity: Activity? = viewManager?.getCurrentView() as? Activity
        if (activity != null) {
            val intent = Intent(activity, Class.forName(view))
            if (data != null) {
                val identifier = viewManager.push(data)
                intent.putExtra(KEY_DATA_IDENTIFIER, identifier)
            }
            activity?.startActivity(intent)
        }
    }
}