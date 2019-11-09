/*
 * Copyright (c) 2019 Ruslan Kishai.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.creageek.segmentedbutton

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.RadioButton

class TextSegment : RadioButton, Segment {

    override fun onStateChanged(isChecked: Boolean) {
        isSelected = isChecked
        typeface = if (isChecked) {
            segmentStyle.segmentFontChecked
        } else {
            segmentStyle.segmentFont
        }
    }

    private val segmentStyle: SegmentStyle

    constructor(context: Context, style: StripStyle) : this(context, null) {
        attachStripStyle(style)
        initSegment()
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        segmentStyle = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SegmentedButton,
            0, 0
        ).toSegmentStyle(context)

        initSegment()
    }

    fun initSegment() {
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, segmentStyle.textSize.toFloat())
        this.typeface = segmentStyle.segmentFont


        val textState =
            buildTextColorStateList(segmentStyle.textColor, segmentStyle.textColorSelected)

        setTextColor(textState)
    }

    fun buildTextColorStateList(color: Int, colorSelected: Int) = ColorStateList(
        arrayOf(
            segmentStyle.stateUnselected,
            segmentStyle.stateSelected
        ), intArrayOf(color, colorSelected)
    )

    fun attachStripStyle(style: StripStyle) {
        segmentStyle.textSize = style.textSize
        segmentStyle.textColor = style.textColor
        segmentStyle.textColorSelected = style.textColorSelected
        segmentStyle.segmentFont = style.segmentFont
        segmentStyle.segmentFontChecked = style.segmentFontChecked
    }
}