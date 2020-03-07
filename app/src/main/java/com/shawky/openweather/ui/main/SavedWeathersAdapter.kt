package com.shawky.openweather.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.shawky.openweather.BuildConfig
import com.shawky.openweather.R
import com.shawky.openweather.data.local.database.dao.WeatherEntity
import com.shawky.openweather.utils.Constants
import kotlinx.android.synthetic.main.item_saved_weather.view.*
import kotlin.properties.Delegates

class SavedWeathersAdapter :
    RecyclerView.Adapter<SavedWeathersAdapter.CategoriesViewHolder>() {

    var itemClickListener: ItemClickListener? = null

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
            holder.itemView.ivIcon.load("${BuildConfig.IMG_BASE_URL}/${weatherEntity.icon}@2x.png")
            holder.itemView.parentLayout.setCardBackgroundColor(Constants.getColor(weatherEntity.dateTime))
            holder.itemView.tvTemperature.text =
                holder.itemView.context.getString(R.string.temp_celsius, weatherEntity.temp)
            weatherEntity.dateTime.let {
                holder.itemView.tvDate.text = Constants.getDateTimeFormatted(it * 1000)
            }

            ViewCompat.setTransitionName(
                holder.itemView.parentLayout,
                "parent_${weatherEntity.dateTime}"
            )
            ViewCompat.setTransitionName(
                holder.itemView.tvTemperature,
                "temp_${weatherEntity.dateTime}"
            )
            ViewCompat.setTransitionName(holder.itemView.ivIcon, "icon_${weatherEntity.dateTime}")

            holder.itemView.setOnClickListener {
                itemClickListener?.onItemClickListener(
                    weatherEntity,
                    holder.itemView.parentLayout,
                    holder.itemView.ivIcon,
                    holder.itemView.tvTemperature
                )
            }
        }
    }


    class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface ItemClickListener {
        fun onItemClickListener(
            item: WeatherEntity,
            cardView: CardView,
            imageView: ImageView,
            tempTextView: TextView
        )
    }
}