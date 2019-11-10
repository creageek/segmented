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
import android.widget.LinearLayout
import android.widget.TextView

class SubtitleSegment : LinearLayout, Segment {

    private var text: TextView? = null
    private var subText: TextView? = null
    private var upperText: TextView? = null

    override fun onStateChanged(state: SegmentState) {
        isSelected = state.value
        if (state.value) {
            upperText?.typeface = subtitleSegmentStyle.upperTitleFontChecked
            text?.typeface = subtitleSegmentStyle.titleFontChecked
            subText?.typeface = subtitleSegmentStyle.subTitleFontChecked
        } else {
            upperText?.typeface = subtitleSegmentStyle.upperTitleFont
            text?.typeface = subtitleSegmentStyle.titleFont
            subText?.typeface = subtitleSegmentStyle.subTitleFont
        }
    }

    private val subtitleSegmentStyle: SubtitleSegmentStyle

    private val title: String?
    private val subTitle: String?
    private val upperTitle: String?

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SegmentedButton,
            0, 0
        )

        with(a) {

            title = getString(R.styleable.SegmentedButton_title)

            subTitle = getString(R.styleable.SegmentedButton_subTitle)

            upperTitle = getString(R.styleable.SegmentedButton_upperTitle)

            subtitleSegmentStyle = a.toSubtitleSegmentStyle(context)

            recycle()
        }

        initWithItems()
    }

    fun initWithItems() {
        upperTitle?.let {
            upperText = TextView(context).apply {
                text = upperTitle
                setTextColor(
                    buildTextColorStateList(
                        subtitleSegmentStyle.upperTitleTextColor,
                        subtitleSegmentStyle.upperTitleTextColorSelected
                    )
                )
                layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
                )
                gravity = subtitleSegmentStyle.upperTitlePosition.toGravity()

                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    subtitleSegmentStyle.upperTitleTextSize.toFloat()
                )
                typeface = subtitleSegmentStyle.upperTitleFont
//                includeFontPadding = false
            }
            addView(upperText)
        }
        title?.let {
            text = TextView(context).apply {
                text = title
                setTextColor(
                    buildTextColorStateList(
                        subtitleSegmentStyle.titleTextColor,
                        subtitleSegmentStyle.titleTextColorSelected
                    )
                )
                layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
                )
                gravity = subtitleSegmentStyle.titlePosition.toGravity()

                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    subtitleSegmentStyle.textSize.toFloat()
                )
                typeface = subtitleSegmentStyle.titleFont
//                includeFontPadding = false
            }
            addView(text)

        }
        subTitle?.let {
            subText = TextView(context).apply {
                text = subTitle
                setTextColor(
                    buildTextColorStateList(
                        subtitleSegmentStyle.subTitleTextColor,
                        subtitleSegmentStyle.subTitleTextColorSelected
                    )
                )
                layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
                )
                gravity = subtitleSegmentStyle.subTitlePosition.toGravity()

                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    subtitleSegmentStyle.subTitleTextSize.toFloat()
                )
                typeface = subtitleSegmentStyle.subTitleFont
//                includeFontPadding = false
            }

            addView(subText)
        }
    }

    fun buildTextColorStateList(color: Int, colorSelected: Int) = ColorStateList(
        arrayOf(
            subtitleSegmentStyle.stateUnselected,
            subtitleSegmentStyle.stateSelected
        ), intArrayOf(color, colorSelected)
    )
}