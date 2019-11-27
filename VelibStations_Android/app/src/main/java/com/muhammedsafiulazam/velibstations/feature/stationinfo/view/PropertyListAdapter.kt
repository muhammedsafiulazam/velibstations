package com.muhammedsafiulazam.velibstations.feature.stationinfo.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.feature.stationinfo.model.Property

/**
 * Created by Muhammed Safiul Azam on 25/07/2019.
 */

class PropertyListAdapter(val contributorList: MutableList<Property>) : RecyclerView.Adapter<PropertyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        return PropertyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_property_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return contributorList.size
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property: Property = contributorList.get(position);
        holder.bind(property)
    }
}