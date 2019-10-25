package com.muhammedsafiulazam.common.utils

import android.app.Activity
import android.content.Intent
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.view.IViewManager
import com.muhammedsafiulazam.common.view.ViewManager

actual object ViewUtils {
    const val KEY_DATA_IDENTIFIER: String = "VIEW_UTILS_KEY_DATA_IDENTIFIER"
    actual fun loadView(view: String?, story: String?, modal: Boolean?, data: Any?) {
        val viewManager: ViewManager? = AddOnManager.getAddOn(AddOnType.VIEW_MANAGEER) as? ViewManager
        val activity: Activity? = viewManager?.getCurrentView() as? Activity
        if (activity != null) {
            val intent = Intent(activity, Class.forName(view))
            if (data != null) {
                val identifier = viewManager?.push(data)
                intent.putExtra(KEY_DATA_IDENTIFIER, identifier)
            }
            activity?.startActivity(intent)
        }
    }
}