package com.creageek.segmentedbutton.style

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import com.creageek.segmentedbutton.R
import com.creageek.segmentedbutton.getColor
import com.creageek.segmentedbutton.getDimensInPixel
import com.creageek.segmentedbutton.getFont

data class InternalTextSegmentStyle(
    var textSize: Int? = null,

    var textColor: Int? = null,
    var textColorSelected: Int? = null,

    var segmentFont: Typeface? = null,
    var segmentFontChecked: Typeface? = null
)


data class TextSegmentStyle(
    var textSize: Int = 0,

    var textColor: Int = 0,
    var textColorSelected: Int = 0,

    var segmentFont: Typeface? = null,
    var segmentFontChecked: Typeface? = null
) {
    fun toInternalTextSegmentStyle() = InternalTextSegmentStyle(
        textSize,
        textColor,
        textColorSelected,
        segmentFont,
        segmentFontChecked
    )
}

fun TypedArray.toTextSegmentStyle(context: Context) = TextSegmentStyle().apply {

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

