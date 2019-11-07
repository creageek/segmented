package com.creageek.segmentedbutton

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.view.View
import kotlin.math.roundToInt

data class Segment(
    val view: View,
    val type: ViewType,
    val style: SegmentStyle
)

enum class ViewType {
    Radio, Text, Image, Custom
}

data class SegmentStyle(
    var spreadType: SegmentSpreadType = SegmentSpreadType.evenly,

    var textSize: Int = 0,
    var segmentHeight: Int = 0,

    var textColor: Int = 0,
    var textColorSelected: Int = 0,
    var segmentFont: Typeface? = null,
    var segmentFontChecked: Typeface? = null,

    var borderColor: Int = 0,
    var borderWidth: Int = 0,
    var borderInnerWidth: Int = 0,
    var r: Float = 0.0f,
    var rI: Float = 0.0f,

    var segmentColor: Int = 0,
    var segmentColorSelected: Int = 0,

    var rippleColor: Int = 0,
    var rippleColorSelected: Int = 0
) {
    // inner values
    val stateChecked = intArrayOf(android.R.attr.state_checked)
    val stateSelected = intArrayOf(android.R.attr.state_selected)
    val stateUnchecked = intArrayOf(-android.R.attr.state_checked)
    val stateUnselected = intArrayOf(-android.R.attr.state_selected)

    val r0 = 0.1f.toPx()
}

fun TypedArray.toSegmentStyle(context: Context) = SegmentStyle().apply {
    // TODO add orientation handling
    spreadType =
        SegmentSpreadType.values()[getInt(
            R.styleable.SegmentedButton_spreadType,
            SegmentSpreadType.wrap.value
        )]

    textSize = getDimensionPixelSize(
        R.styleable.SegmentedButton_textSize,
        context.resources.getDimensionPixelSize(R.dimen.default_segment_text_size)
    )

    segmentHeight = getDimensionPixelSize(
        R.styleable.SegmentedButton_segmentHeight,
        context.resources.getDimensionPixelSize(R.dimen.default_segment_height)
    )

    textColor = getColor(
        R.styleable.SegmentedButton_textColor,
        androidx.core.content.ContextCompat.getColor(
            context,
            R.color.default_text_color
        )
    )

    textColorSelected = getColor(
        R.styleable.SegmentedButton_textColorChecked,
        androidx.core.content.ContextCompat.getColor(
            context,
            R.color.default_text_color_checked
        )
    )

    segmentColor = getColor(
        R.styleable.SegmentedButton_segmentColor,
        androidx.core.content.ContextCompat.getColor(
            context,
            R.color.default_segment_color
        )
    )

    segmentColorSelected = getColor(
        R.styleable.SegmentedButton_segmentColorChecked,
        androidx.core.content.ContextCompat.getColor(
            context,
            R.color.default_segment_color_checked
        )
    )

    borderColor = getColor(
        R.styleable.SegmentedButton_borderColor,
        androidx.core.content.ContextCompat.getColor(
            context,
            R.color.default_border_color
        )
    )

    borderWidth = getDimensionPixelSize(
        R.styleable.SegmentedButton_borderWidth,
        context.resources.getDimensionPixelSize(R.dimen.default_border_width)
    )

    r = getDimensionPixelSize(
        R.styleable.SegmentedButton_cornerRadius,
        context.resources.getDimensionPixelSize(R.dimen.default_corner_radius)
    ).toFloat()

    rippleColor = getColor(
        R.styleable.SegmentedButton_rippleColor,
        androidx.core.content.ContextCompat.getColor(
            context,
            R.color.default_ripple_color
        )
    )

    rippleColorSelected = getColor(
        R.styleable.SegmentedButton_rippleColorChecked,
        androidx.core.content.ContextCompat.getColor(
            context,
            R.color.default_ripple_color_checked
        )
    )

    borderInnerWidth = (borderWidth / 2f).roundToInt()
    rI = r - borderInnerWidth

    val segmentFontId =
        getResourceId(R.styleable.SegmentedButton_segmentFont, -1)
    if (segmentFontId != -1) {
        segmentFont = androidx.core.content.res.ResourcesCompat.getFont(context, segmentFontId)
    }

    val segmentFontCheckedId = getResourceId(
        R.styleable.SegmentedButton_segmentFontChecked,
        -1
    )
    if (segmentFontCheckedId != -1) {
        segmentFontChecked =
            androidx.core.content.res.ResourcesCompat.getFont(context, segmentFontCheckedId)
    } else if (segmentFontId != -1) {
        segmentFontChecked = segmentFont
    }

    recycle()
}