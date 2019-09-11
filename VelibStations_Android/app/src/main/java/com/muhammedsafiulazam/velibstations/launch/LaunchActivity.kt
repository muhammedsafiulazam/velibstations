package com.muhammedsafiulazam.velibstations.launch

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.muhammedsafiulazam.common.database.velib.event.VelibDatabaseEventType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.EventSubscriber
import com.muhammedsafiulazam.common.event.IEventSubscriber
import com.muhammedsafiulazam.common.knowledge.Knowledge
import com.muhammedsafiulazam.common.service.velib.event.VelibServiceEventType
import com.muhammedsafiulazam.velibstations.R
import kotlinx.coroutines.channels.ReceiveChannel


/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class LaunchActivity : AppCompatActivity() {
    private var mEventSubscriber: IEventSubscriber? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        mEventSubscriber = Knowledge.getEventManager().subscribe( callback = { event : Event -> Unit
            onReceiveEvents(event)
        })

        Knowledge.getServiceManager().getVelibService().getData()
        //Knowledge.getDatabaseManager().getVelibDatabase().getData()
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
        Knowledge.getEventManager().unsubscribe(mEventSubscriber)
        super.onDestroy()
    }
}