package com.artemObrazumov.drinkin.address.presentation.addressSelect.components

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.DrawableCompat.setTint
import com.artemObrazumov.drinkin.R

fun cafeMarkerView(
    context: Context,
    iconColor: Int,
    sizePx: Int = 164,
    iconSizePx: Int = 96
): View {
    return RelativeLayout(context).apply {
        val iconImageView = ImageView(context).apply {
            val drawable = AppCompatResources.getDrawable(context, R.drawable.coffee_icon)!!
            setTint(drawable, iconColor)
            setImageDrawable(drawable)
            val params = RelativeLayout.LayoutParams(iconSizePx, iconSizePx)
            params.addRule(RelativeLayout.CENTER_IN_PARENT)
            layoutParams = params
        }
        addView(iconImageView)
        minimumWidth = sizePx
        minimumHeight = sizePx
        background = AppCompatResources.getDrawable(
            context,
            R.drawable.marker_circle_background
        )
    }
}