package com.muhammedsafiulazam.mobile.service.velib

import com.muhammedsafiulazam.mobile.event.Event
import com.muhammedsafiulazam.mobile.knowledge.Knowledge
import com.muhammedsafiulazam.mobile.service.ServiceClient
import com.muhammedsafiulazam.mobile.service.model.Error
import com.muhammedsafiulazam.mobile.service.velib.event.VelibEventType
import com.muhammedsafiulazam.mobile.service.velib.model.Data

class VelibService(val serviceClient: ServiceClient) : IVelibService {

    val URL_VELIB = "dataset=velib-disponibilite-en-temps-reel&facet=overflowactivation&facet=creditcard&facet=kioskstate&facet=station_state"

    override fun getData(latitude: Double, longitude: Double, radius: Double, index: Int, count: Int) {
        val url = "${URL_VELIB}&geofilter.distance=${latitude}%2C${longitude}%2C${radius}&start=${index}&rows=${count}"

        serviceClient.call(url, callback = { response: Data?, error: Error? ->
            val event = Event(VelibEventType.GET_DATA, response, error)
            Knowledge.getEventManager().send(event)
        })
    }
}