package com.muhammedsafiulazam.velibstations.launch

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.database.IDatabaseManager
import com.muhammedsafiulazam.common.database.velib.event.VelibDatabaseEventType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.event.IEventSubscriber
import com.muhammedsafiulazam.common.service.IServiceManager
import com.muhammedsafiulazam.common.service.velib.event.VelibServiceEventType
import com.muhammedsafiulazam.velibstations.R


/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class LaunchActivity : AppCompatActivity() {
    // Managers.
    private val mEventManager: IEventManager = AddOnManager.getAddOn(AddOnType.EVENT_MANAGER)!! as IEventManager
    private val mDatabaseManager: IDatabaseManager = AddOnManager.getAddOn(AddOnType.DATABASE_MANAGER)!! as IDatabaseManager
    private val mServiceManager: IServiceManager = AddOnManager.getAddOn(AddOnType.SERVICE_MANAGER)!! as IServiceManager

    private var mEventSubscriber: IEventSubscriber? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        mEventSubscriber = mEventManager.subscribe( callback = { event : Event -> Unit
            onReceiveEvents(event)
        })

        mServiceManager.getVelibService().getData()
        //mDatabaseManager.getVelibDatabase().getData()
    }

    fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(VelibServiceEventType.GET_DATA, event.type)) {
            println("BEGIN: REMOTE DATA")
            if (event.error != null) {
                println(event.error.toString())
            } else {
                println(event.data.toString())
            }
            println("END: REMOTE DATA")
        } else if (TextUtils.equals(VelibDatabaseEventType.GET_DATA, event.type)) {
            println("BEGIN: LOCAL DATA")
            println(event.data.toString())
            println("END: LOCAL DATA")
        }
    }

    override fun onDestroy() {
        mEventManager.unsubscribe(mEventSubscriber)
        super.onDestroy()
    }
}