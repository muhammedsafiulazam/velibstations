package com.muhammedsafiulazam.common.service.velib

import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.knowledge.Knowledge
import com.muhammedsafiulazam.common.service.ServiceClient
import com.muhammedsafiulazam.common.service.model.Error
import com.muhammedsafiulazam.common.service.velib.event.VelibEventType
import com.muhammedsafiulazam.common.service.velib.model.Dataset

class VelibService(val serviceClient: ServiceClient) : IVelibService {

    val URL_VELIB = "dataset=velib-disponibilite-en-temps-reel&facet=overflowactivation&facet=creditcard&facet=kioskstate&facet=station_state"

    override fun getData(latitude: Double, longitude: Double, radius: Double, index: Int, count: Int) {
        val url = "${URL_VELIB}&geofilter.distance=${latitude}%2C${longitude}%2C${radius}&start=${index}&rows=${count}"

        serviceClient.call(url, callback = { response: Dataset?, error: Error? ->
            val event = Event(VelibEventType.GET_DATA, response, error)
            Knowledge.getEventManager().send(event)
        })
    }
}