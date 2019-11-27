package com.muhammedsafiulazam.common.database.velib

import com.muhammedsafiulazam.common.addon.AddOn
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.database.velib.event.VelibDatabaseEventType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.service.velib.model.Dataset
import com.muhammedsafiulazam.common.service.velib.model.Fields
import com.muhammedsafiulazam.common.service.velib.model.Parameters
import com.muhammedsafiulazam.common.service.velib.model.Record
import com.muhammedsafiulazam.common.utils.CoroutineUtils
import com.squareup.sqldelight.Query
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class VelibDatabase(val db: VelibDB) : AddOn(), IVelibDatabase {
    override fun getData(latitude: Double, longitude: Double, radius: Double)  {
        GlobalScope.launch(CoroutineUtils.DISPATCHER_IO) {

            var records: ArrayList<Record> = arrayListOf()

            getRecords()?.forEach { record ->
                if (record.isWithinGeofence(latitude, longitude, radius)) {
                    records.add(record)
                }
            }

            val count: Int = records.size
            val parameters: Parameters = Parameters(null, 0, count)
            val dataset: Dataset = Dataset(count, parameters, records)

            val event = Event(VelibDatabaseEventType.GET_DATA, dataset, null)
            val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
            eventManager?.send(event)
        }
    }

    override fun saveData(dataset: Dataset?) {
        GlobalScope.launch(CoroutineUtils.DISPATCHER_IO) {
            dataset?.records?.forEach { record ->
                saveRecord(record)
            }

            val event = Event(VelibDatabaseEventType.SAVE_DATA, dataset, null)
            val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
            eventManager?.send(event)
        }
    }

    override fun cleanData() {
        GlobalScope.launch(CoroutineUtils.DISPATCHER_IO) {
            db.recordTableQueries.deleteAll()
            db.fieldsTableQueries.deleteAll()

            val event = Event(VelibDatabaseEventType.CLEAN_DATA, null, null)
            val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
            eventManager?.send(event)
        }
    }

    private fun getRecords() : ArrayList<Record>? {
        val recordList: ArrayList<Record> = arrayListOf()

        val recordTableQueries: Query<com.muhammedsafiulazam.common.database.velib.schema.RecordTable> = db.recordTableQueries.selectAll()
        recordTableQueries.executeAsList().forEach { recordQuery ->

            val fieldsTableQueries: Query<com.muhammedsafiulazam.common.database.velib.schema.FieldsTable> = db.fieldsTableQueries.selectByRecord(recordQuery.id)
            fieldsTableQueries.executeAsList().forEach { fieldQuery ->

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

    private fun saveRecord(record: Record?) {
        val id = record?.id!!
        val fields = record?.fields!!

        db.fieldsTableQueries.insert(id,
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

        db.recordTableQueries.insert(id, record?.timestamp)
    }
}