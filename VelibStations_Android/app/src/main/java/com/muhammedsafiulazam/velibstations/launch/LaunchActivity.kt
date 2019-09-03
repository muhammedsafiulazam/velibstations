package com.muhammedsafiulazam.velibstations.launch

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.muhammedsafiulazam.common.database.velib.schema.Record
import com.muhammedsafiulazam.common.database.velib.schema.RecordQueries
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.knowledge.Knowledge
import com.muhammedsafiulazam.common.service.velib.event.VelibEventType
import com.muhammedsafiulazam.common.service.velib.model.Fields
import com.muhammedsafiulazam.velibstations.R
import com.squareup.sqldelight.Query
import kotlinx.coroutines.channels.ReceiveChannel
import java.util.*


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
    }

    fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(VelibEventType.GET_DATA, event.type)) {
            var message: String

            if (event.error != null) {
                message = event.error.toString()
            } else {
                message = event.data.toString()
            }

            println(message)
        }
    }

    override fun onDestroy() {
        Knowledge.getEventManager().unsubscribe(mReceiveChannel)
        super.onDestroy()
    }
}