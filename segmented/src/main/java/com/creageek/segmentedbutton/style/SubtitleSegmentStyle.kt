package com.creageek.segmentedbutton.style

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import com.creageek.segmentedbutton.*

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