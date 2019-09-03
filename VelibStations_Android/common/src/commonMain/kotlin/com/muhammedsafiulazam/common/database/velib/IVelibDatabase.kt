package com.muhammedsafiulazam.common.database.velib

import com.muhammedsafiulazam.common.service.velib.model.Record

interface IVelibDatabase {
    fun selectAllRecords()
    fun insertRecord(record: Record)
    fun deleteRecord(record: Record)
    fun deleteAllRecords()
}