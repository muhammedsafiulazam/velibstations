package com.muhammedsafiulazam.velibstations.launch

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.muhammedsafiulazam.common.database.velib.event.VelibDatabaseEventType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.knowledge.Knowledge
import com.muhammedsafiulazam.common.service.velib.event.VelibServiceEventType
import com.muhammedsafiulazam.common.service.velib.model.Record
import com.muhammedsafiulazam.velibstations.R
import kotlinx.coroutines.channels.ReceiveChannel


/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class LaunchActivity : AppCompatActivity() {
    private var mReceiveChannel: ReceiveChannel<Event>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        mReceiveChannel = Knowledge.getEventManager().subscribe( callback = { event : Event -> Unit
            onReceiveEvents(event)
        })

        Knowledge.getServiceManager().getVelibService().getData()
        //Knowledge.getDatabaseManager().getVelibDatabase().getRecords()
    }

    fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(VelibServiceEventType.GET_DATA, event.type)) {
            println("BEGIN: GET DATA")
            if (event.error != null) {
                println(event.error.toString())
            } else {
                println(event.data.toString())
            }
            println("END: GET DATA")
        } else if (TextUtils.equals(VelibDatabaseEventType.GET_RECORDS, event.type)) {
            println("BEGIN: GET RECORDS")
            println(event.data.toString())
            println("END: GET RECORDS")

        } else if (TextUtils.equals(VelibDatabaseEventType.ADD_RECORDS, event.type)) {
            println("BEGIN: ADD RECORDS")
            println(event.data.toString())
            println("END: ADD RECORDS")
        }
    }

    override fun onDestroy() {
        Knowledge.getEventManager().unsubscribe(mReceiveChannel)
        super.onDestroy()
    }
}