package com.zomato.view.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.zomato.R
import com.zomato.model.RatingData
import java.util.*

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun imageUrl(view: ImageView, url: String?) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(view.context).load(url)
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image).into(view)
        } else {
            view.setImageResource(R.drawable.no_image)
        }
    }

    @JvmStatic
    @BindingAdapter("rating")
    fun rating(view: AppCompatTextView, ratingData: RatingData) {

        (view.background as GradientDrawable).color = ColorStateList
            .valueOf(Color.parseColor("#${ratingData.color}"))

        view.text = ratingData.rating.title.text
        view.visibility = when (ratingData.rating.title.text) {
            "-" -> View.GONE
            else -> View.VISIBLE
        }
    }

}