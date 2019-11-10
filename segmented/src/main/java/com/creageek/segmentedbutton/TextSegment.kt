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

class TextSegment : RadioButton, Segment {

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
        setColorStateListOf(segmentStyle.textColor, segmentStyle.textColorSelected)
    }

    private fun attachStripStyle(style: StripStyle) {
        segmentStyle.apply {
            textSize = style.textSize
            textColor = style.textColor
            textColorSelected = style.textColorSelected
            segmentFont = style.segmentFont
            segmentFontChecked = style.segmentFontChecked
        }
    }
}