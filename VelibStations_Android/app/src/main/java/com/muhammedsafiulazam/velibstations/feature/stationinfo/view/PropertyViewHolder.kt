package com.muhammedsafiulazam.velibstations.feature.stationinfo.view

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.feature.stationinfo.model.Property

/**
 * Created by Muhammed Safiul Azam on 25/07/2019.
 */

class PropertyViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private var mView: View? = null
    private var mProperty: Property? = null
    private var mTxvTitle: AppCompatTextView? = null
    private var mTxvValue: AppCompatTextView? = null

    init {
        mView = view
        mTxvTitle = view.findViewById(R.id.property_txv_title)
        mTxvValue = view.findViewById(R.id.property_txv_value)
    }

    fun bind(property: Property) {
        mProperty = property

        mTxvTitle!!.text = mProperty!!.title
        mTxvValue!!.text = mProperty!!.value.capitalize()
    }
}