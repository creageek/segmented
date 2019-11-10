package com.creageek.segmentedbutton

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import kotlin.math.roundToInt


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
}

data class SegmentStyle(
    var textSize: Int = 0,

    var textColor: Int = 0,
    var textColorSelected: Int = 0,

    var segmentFont: Typeface? = null,
    var segmentFontChecked: Typeface? = null
)

data class SubtitleSegmentStyle(
    var titleType: TextType = TextType.multiline,
    var subTitleType: TextType = TextType.multiline,
    var upperTitleType: TextType = TextType.multiline,

    var titlePosition: TextPosition = TextPosition.start,
    var subTitlePosition: TextPosition = TextPosition.start,
    var upperTitlePosition: TextPosition = TextPosition.start,

    var textSize: Int = 0,
    var subTitleTextSize: Int = 0,
    var upperTitleTextSize: Int = 0,

    var titleTextColor: Int = 0,
    var subTitleTextColor: Int = 0,
    var upperTitleTextColor: Int = 0,

    var titleTextColorSelected: Int = 0,
    var subTitleTextColorSelected: Int = 0,
    var upperTitleTextColorSelected: Int = 0,

    var titleFont: Typeface? = null,
    var upperTitleFont: Typeface? = null,
    var subTitleFont: Typeface? = null,

    var titleFontChecked: Typeface? = null,
    var subTitleFontChecked: Typeface? = null,
    var upperTitleFontChecked: Typeface? = null
) {
    // inner values
    val stateSelected = intArrayOf(android.R.attr.state_selected)
    val stateUnselected = intArrayOf(-android.R.attr.state_selected)
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


fun TypedArray.toSegmentStyle(context: Context) = SegmentStyle().apply {

    textSize = getDimensInPixel(
        context,
        R.styleable.SegmentedButton_textSize,
        R.dimen.default_segment_text_size
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

    segmentFont = getFont(context, R.styleable.SegmentedButton_segmentFont)

    segmentFontChecked =
        getFont(context, R.styleable.SegmentedButton_segmentFontChecked, segmentFont)

    recycle()
}


fun TypedArray.toSubtitleSegmentStyle(context: Context) = SubtitleSegmentStyle().apply {

    titleType =
        TextType.values()[getInt(
            R.styleable.SegmentedButton_titleType,
            TextType.multiline.value
        )]

    subTitleType =
        TextType.values()[getInt(
            R.styleable.SegmentedButton_subTitleType,
            TextType.multiline.value
        )]

    upperTitleType =
        TextType.values()[getInt(
            R.styleable.SegmentedButton_upperTitleType,
            TextType.multiline.value
        )]

    titlePosition =
        TextPosition.values()[getInt(
            R.styleable.SegmentedButton_titlePosition,
            TextPosition.start.value
        )]

    subTitlePosition =
        TextPosition.values()[getInt(
            R.styleable.SegmentedButton_subTitlePosition,
            TextPosition.start.value
        )]

    upperTitlePosition =
        TextPosition.values()[getInt(
            R.styleable.SegmentedButton_upperTitlePosition,
            TextPosition.start.value
        )]

    textSize = getDimensInPixel(
        context,
        R.styleable.SegmentedButton_textSize,
        R.dimen.default_segment_text_size
    )

    subTitleTextSize = getDimensInPixel(
        context,
        R.styleable.SegmentedButton_subTitleTextSize,
        R.dimen.default_segment_text_size
    )

    upperTitleTextSize = getDimensInPixel(
        context,
        R.styleable.SegmentedButton_upperTitleTextSize,
        R.dimen.default_segment_text_size
    )

    titleTextColor = getColor(
        context,
        R.styleable.SegmentedButton_textColor,
        R.color.default_text_color
    )


    subTitleTextColor = getColor(
        context,
        R.styleable.SegmentedButton_subTitleTextColor,
        R.color.default_text_color
    )

    upperTitleTextColor = getColor(
        context,
        R.styleable.SegmentedButton_upperTitleTextColor,
        R.color.default_text_color
    )

    titleTextColorSelected = getColor(
        context,
        R.styleable.SegmentedButton_textColorChecked,
        R.color.default_text_color_checked
    )

    subTitleTextColorSelected = getColor(
        context,
        R.styleable.SegmentedButton_subTitleTextColorChecked,
        R.color.default_text_color_checked
    )

    upperTitleTextColorSelected = getColor(
        context,
        R.styleable.SegmentedButton_upperTitleTextColorChecked,
        R.color.default_text_color_checked
    )

    titleFont = getFont(context, R.styleable.SegmentedButton_segmentFont)

    subTitleFont = getFont(context, R.styleable.SegmentedButton_subTitleSegmentFont)

    upperTitleFont = getFont(context, R.styleable.SegmentedButton_upperTitleSegmentFont)

    titleFontChecked = getFont(context, R.styleable.SegmentedButton_segmentFontChecked, titleFont)

    subTitleFontChecked =
        getFont(context, R.styleable.SegmentedButton_subTitleSegmentFontChecked, subTitleFont)

    upperTitleFontChecked =
        getFont(context, R.styleable.SegmentedButton_upperTitleSegmentFontChecked, upperTitleFont)
}