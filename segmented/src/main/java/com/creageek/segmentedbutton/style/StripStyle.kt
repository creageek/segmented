package com.creageek.segmentedbutton.style

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import com.creageek.segmentedbutton.*
import kotlin.math.roundToInt

data class InternalStripStyle(
    var segmentGravity: SegmentGravity? = null,

    var spreadType: SegmentSpreadType? = null,

    var textSize: Int? = null,
    var segmentHeight: Int? = null,

    var textColor: Int? = null,
    var textColorSelected: Int? = null,
    var segmentFont: Typeface? = null,
    var segmentFontChecked: Typeface? = null,

    var borderColor: Int? = null,
    var borderWidth: Int? = null,
    var r: Float? = null,

    var segmentColor: Int? = null,
    var segmentColorSelected: Int? = null,

    var rippleColor: Int? = null,
    var rippleColorSelected: Int? = null
)


data class StripStyle(
    var segmentGravity: SegmentGravity = SegmentGravity.top,

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
    val stateSelected = intArrayOf(android.R.attr.state_selected)
    val stateUnselected = intArrayOf(-android.R.attr.state_selected)

    val r0 = 0.1f.toPx()

    fun toInternalStyle() = InternalStripStyle(
        segmentGravity,
        spreadType,
        textSize,
        segmentHeight,
        textColor,
        textColorSelected,
        segmentFont,
        segmentFontChecked,
        borderColor,
        borderWidth,
        r,
        segmentColor,
        segmentColorSelected,
        rippleColor,
        rippleColorSelected
    )
}


fun TypedArray.toStripStyle(context: Context) = StripStyle().apply {

    segmentGravity =
        SegmentGravity.values()[getInt(
            R.styleable.SegmentedButton_segmentGravity,
            SegmentGravity.top.value
        )]

    spreadType =
        SegmentSpreadType.values()[getInt(
            R.styleable.SegmentedButton_spreadType,
            SegmentSpreadType.wrap.value
        )]

    textSize = getDimensInPixel(
        context,
        R.styleable.SegmentedButton_textSize,
        R.dimen.default_segment_text_size
    )

    segmentHeight = getDimensInPixel(
        context,
        R.styleable.SegmentedButton_segmentHeight,
        R.dimen.default_segment_height
    )

    textColor = getColor(
        context,
        R.styleable.SegmentedButton_textColor,
        R.color.default_text_color
    )

    textColorSelected = getColor(
        context,
        R.styleable.SegmentedButton_textColorChecked,
        R.color.default_text_color_checked
    )

    segmentColor = getColor(
        context,
        R.styleable.SegmentedButton_segmentColor,
        R.color.default_segment_color
    )

    segmentColorSelected = getColor(
        context,
        R.styleable.SegmentedButton_segmentColorChecked,
        R.color.default_segment_color_checked
    )

    borderColor = getColor(
        context,
        R.styleable.SegmentedButton_borderColor,
        R.color.default_border_color
    )

    borderWidth = getDimensInPixel(
        context,
        R.styleable.SegmentedButton_borderWidth,
        R.dimen.default_border_width
    )

    r = getDimensInPixel(
        context,
        R.styleable.SegmentedButton_cornerRadius,
        R.dimen.default_corner_radius
    ).toFloat()

    rippleColor = getColor(
        context,
        R.styleable.SegmentedButton_rippleColor,
        R.color.default_ripple_color
    )

    rippleColorSelected = getColor(
        context,
        R.styleable.SegmentedButton_rippleColorChecked,
        R.color.default_ripple_color_checked
    )

    borderInnerWidth = (borderWidth / 2f).roundToInt()
    rI = r - borderInnerWidth

    segmentFont = getFont(context, R.styleable.SegmentedButton_segmentFont)

    segmentFontChecked =
        getFont(context, R.styleable.SegmentedButton_segmentFontChecked, segmentFont)

    recycle()
}
