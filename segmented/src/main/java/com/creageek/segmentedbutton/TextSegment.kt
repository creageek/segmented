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
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity.CENTER
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RadioGroup.LayoutParams.MATCH_PARENT
import android.widget.RadioGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import kotlin.math.roundToInt

class TextSegment : RadioButton, Segment {
    private val textSize: Int

    private val textColor: Int
    private val textColorSelected: Int
    private var segmentFont: Typeface? = null
    private var segmentFontChecked: Typeface? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){

        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SegmentedButton,
            0, 0
        )

        with(a) {

            textSize = getDimensionPixelSize(
                R.styleable.SegmentedButton_textSize,
                context.resources.getDimensionPixelSize(R.dimen.default_segment_text_size)
            )

            textColor = getColor(
                R.styleable.SegmentedButton_textColor,
                ContextCompat.getColor(context, R.color.default_text_color)
            )

            textColorSelected = getColor(
                R.styleable.SegmentedButton_textColorChecked,
                ContextCompat.getColor(context, R.color.default_text_color_checked)
            )

            val segmentFontId = getResourceId(R.styleable.SegmentedButton_segmentFont, -1)
            if (segmentFontId != -1) {
                segmentFont = ResourcesCompat.getFont(context, segmentFontId)
            }

            val segmentFontCheckedId = getResourceId(R.styleable.SegmentedButton_segmentFontChecked, -1)
            if (segmentFontCheckedId != -1) {
                segmentFontChecked = ResourcesCompat.getFont(context, segmentFontCheckedId)
            } else if (segmentFontId != -1) {
                segmentFontChecked = segmentFont
            }

            recycle()
        }
    }

}