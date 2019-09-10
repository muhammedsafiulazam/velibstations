package com.muhammedsafiulazam.common.database.velib

import com.muhammedsafiulazam.common.database.velib.event.VelibDatabaseEventType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.knowledge.Knowledge
import com.muhammedsafiulazam.common.service.velib.model.Dataset
import com.muhammedsafiulazam.common.service.velib.model.Fields
import com.muhammedsafiulazam.common.service.velib.model.Parameters
import com.muhammedsafiulazam.common.service.velib.model.Record
import com.muhammedsafiulazam.common.utils.CouroutineUtils
import com.squareup.sqldelight.Query
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class VelibDatabase(val db: VelibDB) : IVelibDatabase {
    override fun getData(latitude: Double, longitude: Double, radius: Double)  {
        GlobalScope.launch(CouroutineUtils.DISPATCHER) {
            val records: ArrayList<Record> = arrayListOf()

            getRecords()?.forEach { record ->
                if (record.isWithinGeofence(latitude, longitude, radius)) {
                    records.add(record)
                }
            }

            val count: Int = records.size
            val parameters: Parameters = Parameters(null, 0, count)
            val dataset: Dataset = Dataset(count, parameters, records)

            val event = Event(VelibDatabaseEventType.GET_DATA, dataset, null)
            Knowledge.getEventManager().send(event)
        }
    }

    override fun addData(dataset: Dataset?) {
        GlobalScope.launch(CouroutineUtils.DISPATCHER) {
            dataset?.records?.forEach { record ->
                addRecord(record)
            }

            val event = Event(VelibDatabaseEventType.ADD_DATA, dataset, null)
            Knowledge.getEventManager().send(event)
        }
    }

    override fun cleanData() {
        GlobalScope.launch(CouroutineUtils.DISPATCHER) {
            db.recordQueries.deleteAll()
            db.fieldsQueries.deleteAll()

            val event = Event(VelibDatabaseEventType.CLEAN_DATA, null, null)
            Knowledge.getEventManager().send(event)
        }
    }

    private fun getRecords() : ArrayList<Record>? {
        val recordList: ArrayList<Record> = arrayListOf()

        val recordQueries: Query<com.muhammedsafiulazam.common.database.velib.schema.Record> = db.recordQueries.selectAll()
        recordQueries.executeAsList().forEach { recordQuery ->

            val fieldsQueries: Query<com.muhammedsafiulazam.common.database.velib.schema.Fields> = db.fieldsQueries.selectByRecord(recordQuery.id)
            fieldsQueries.executeAsList().forEach { fieldQuery ->

                val fields: Fields = Fields(
                    fieldQuery.name,
                    fieldQuery.code,
                    fieldQuery.state,
                    fieldQuery.type,
                    fieldQuery.dueDate,
                    arrayListOf(fieldQuery.latitude!!, fieldQuery.longitude!!),
                    fieldQuery.kioskState,
                    fieldQuery.creditCard,
                    fieldQuery.overflowActivation,
                    fieldQuery.maxBikeOverflow,
                    fieldQuery.nbEDock,
                    fieldQuery.nbFreeEDock,
                    fieldQuery.nbDock,
                    fieldQuery.nbFreeDock,
                    fieldQuery.nbEBike,
                    fieldQuery.nbBike,
                    fieldQuery.nbBikeOverflow,
                    fieldQuery.nbEBikeOverflow,
                    fieldQuery.overflow,
                    fieldQuery.densityLevel
                )

                val record: Record = Record(recordQuery.id, recordQuery.timestamp, fields)
                recordList.add(record)
            }
        }
        return recordList
    }

    private fun addRecord(record: Record?) {
        val id = record?.id!!
        val fields = record?.fields!!

        db.fieldsQueries.insert(id,
            fields.name,
            fields.code!!,
            fields.state,
            fields.type,
            fields.dueDate,
            fields.geolocation?.get(0)!!,
            fields.geolocation?.get(1)!!,
            fields.kioskState,
            fields.creditCard,
            fields.overflowActivation,
            fields.maxBikeOverflow,
            fields.nbEDock,
            fields.nbFreeEDock,
            fields.nbDock,
            fields.nbFreeDock,
            fields.nbEBike,
            fields.nbBike,
            fields.nbBikeOverflow,
            fields.nbEBikeOverflow,
            fields.overflow,
            fields.densityLevel)

        db.recordQueries.insert(id, record?.timestamp)
    }
}