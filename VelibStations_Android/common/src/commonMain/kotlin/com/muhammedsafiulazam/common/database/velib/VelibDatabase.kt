package com.muhammedsafiulazam.common.database.velib

import com.muhammedsafiulazam.common.database.velib.schema.Fields
import com.muhammedsafiulazam.common.service.velib.model.Record
import com.squareup.sqldelight.Query


class VelibDatabase(val db: VelibDB) : IVelibDatabase {
    override fun selectAllRecords() {
        val recordQueries: Query<com.muhammedsafiulazam.common.database.velib.schema.Record> = db.recordQueries.selectAll()
        recordQueries.executeAsList().forEach { recordQuery ->
            val fieldsQueries: Query<Fields> = db.fieldsQueries.selectByRecord(recordQuery.id)
            fieldsQueries.executeAsList().forEach { fieldQuery ->
                // Initialize models here.
            }
        }
    }

    override fun insertRecord(record: Record) {
        // Insert fields here.
        db.recordQueries.insert(record.id!!, record.timestamp)
    }

    override fun deleteRecord(record: Record) {
        db.recordQueries.delete(record.id!!)
        db.fieldsQueries.deleteByRecord(record.id)
    }

    override fun deleteAllRecords() {
        db.recordQueries.deleteAll()
        db.fieldsQueries.deleteAll()
    }
}