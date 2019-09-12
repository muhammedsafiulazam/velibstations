package com.muhammedsafiulazam.common.service.velib

import com.muhammedsafiulazam.common.addon.AddOn
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.database.IDatabaseManager
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.service.ServiceClient
import com.muhammedsafiulazam.common.service.model.Error
import com.muhammedsafiulazam.common.service.velib.event.VelibServiceEventType
import com.muhammedsafiulazam.common.service.velib.model.Dataset

class VelibService(val serviceClient: ServiceClient) : AddOn(), IVelibService {

    val URL_VELIB = "dataset=velib-disponibilite-en-temps-reel&facet=overflowactivation&facet=creditcard&facet=kioskstate&facet=station_state"

    override fun getData(latitude: Double, longitude: Double, radius: Double, index: Int, count: Int) {
        val url = "${URL_VELIB}&geofilter.distance=${latitude}%2C${longitude}%2C${radius}&start=${index}&rows=${count}"

        serviceClient.call(url, callback = { response: Dataset?, error: Error? ->
            // Managers.
            val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
            val databaseManager: IDatabaseManager? = getAddOn(AddOnType.DATABASE_MANAGER) as IDatabaseManager?

            // Save data.
            databaseManager!!.getVelibDatabase().addData(response)

            // Dispatch event.
            val event = Event(VelibServiceEventType.GET_DATA, response, error)
            eventManager!!.send(event)
        })
    }
}