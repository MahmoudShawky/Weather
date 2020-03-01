package com.shawky.openweather.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shawky.openweather.R
import com.shawky.openweather.data.local.database.dao.WeatherEntity
import com.shawky.openweather.utils.Constants
import kotlinx.android.synthetic.main.item_saved_weather.view.*
import kotlin.properties.Delegates

class SavedWeathersAdapter(var itemClickListener: ((WeatherEntity) -> Unit)? = null) :
    RecyclerView.Adapter<SavedWeathersAdapter.CategoriesViewHolder>() {

    private var weathers: List<WeatherEntity> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun updateWeathers(newWeathers: List<WeatherEntity>) {
        weathers = newWeathers
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_saved_weather,
                parent,
                false
            )

        return CategoriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weathers.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        // Verify if position exists in list
        if (position != RecyclerView.NO_POSITION) {
            val weatherEntity: WeatherEntity = weathers[position]
            holder.itemView.tvTemperature.text =
                holder.itemView.context.getString(R.string.temp_celsius, weatherEntity.temp)
            weatherEntity.dateTime.let {
                holder.itemView.tvDate.text = Constants.getDateTimeFormatted(it * 1000)
            }
            holder.itemView.setOnClickListener {
                itemClickListener?.invoke(weatherEntity)
            }
        }
    }


    class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}