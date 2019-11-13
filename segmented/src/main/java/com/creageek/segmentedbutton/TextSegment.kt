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
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.RadioButton
import com.creageek.segmentedbutton.style.InternalTextSegmentStyle
import com.creageek.segmentedbutton.style.StripStyle
import com.creageek.segmentedbutton.style.TextSegmentStyle
import com.creageek.segmentedbutton.style.toTextSegmentStyle

class TextSegment : RadioButton, Segment {

    private var segmentStyle: TextSegmentStyle
        set(value) {
            field = value
            initSegment()
        }

    fun updateStyles(style: InternalTextSegmentStyle) {
        style.textSize?.let { segmentStyle.textSize = it }
        style.textColor?.let { segmentStyle.textColor = it }
        style.textColorSelected?.let { segmentStyle.textColorSelected = it }
        style.segmentFont?.let { segmentStyle.segmentFont = it }
        style.segmentFontChecked?.let { segmentStyle.segmentFontChecked = it }

        initSegment()
    }

    constructor(context: Context, text: String) : this(context, null) {
        this.text = text
    }

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
        ).toTextSegmentStyle(context)
    }

    override fun onStateChanged(state: SegmentState) {
        isSelected = state.value
        typeface = if (state.value) {
            segmentStyle.segmentFontChecked
        } else {
            segmentStyle.segmentFont
        }
    }

    private fun initSegment() {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, segmentStyle.textSize.toFloat())
        typeface = segmentStyle.segmentFont
        includeFontPadding = false
        setColorStateListOf(segmentStyle.textColor, segmentStyle.textColorSelected)
    }

    fun attachStripStyle(style: StripStyle) {
        segmentStyle.apply {
            textSize = style.textSize
            textColor = style.textColor
            textColorSelected = style.textColorSelected
            segmentFont = style.segmentFont
            segmentFontChecked = style.segmentFontChecked
        }
    }
}