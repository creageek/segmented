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
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import com.creageek.segmentedbutton.style.InternalSubtitleSegmentStyle
import com.creageek.segmentedbutton.style.SubtitleSegmentStyle
import com.creageek.segmentedbutton.style.toSubtitleSegmentStyle

class SubtitleSegment : LinearLayout, Segment {

    private var subtitleSegmentStyle: SubtitleSegmentStyle
        set(value) {
            field = value
            initWithItems()
        }

    fun updateStyles(style: InternalSubtitleSegmentStyle) {
        style.titleType?.let { subtitleSegmentStyle.titleType = it }
        style.subTitleType?.let { subtitleSegmentStyle.subTitleType = it }
        style.upperTitleType?.let { subtitleSegmentStyle.upperTitleType = it }
        style.titlePosition?.let { subtitleSegmentStyle.titlePosition = it }
        style.subTitlePosition?.let { subtitleSegmentStyle.subTitlePosition = it }
        style.upperTitlePosition?.let { subtitleSegmentStyle.upperTitlePosition = it }
        style.textSize?.let { subtitleSegmentStyle.textSize = it }
        style.subTitleTextSize?.let { subtitleSegmentStyle.subTitleTextSize = it }
        style.upperTitleTextSize?.let { subtitleSegmentStyle.upperTitleTextSize = it }
        style.titleTextColor?.let { subtitleSegmentStyle.titleTextColor = it }
        style.subTitleTextColor?.let { subtitleSegmentStyle.subTitleTextColor = it }
        style.upperTitleTextColor?.let { subtitleSegmentStyle.upperTitleTextColor = it }
        style.titleTextColorSelected?.let { subtitleSegmentStyle.titleTextColorSelected = it }
        style.subTitleTextColorSelected?.let { subtitleSegmentStyle.subTitleTextColorSelected = it }
        style.upperTitleTextColorSelected?.let {
            subtitleSegmentStyle.upperTitleTextColorSelected = it
        }
        style.titleFont?.let { subtitleSegmentStyle.titleFont = it }
        style.upperTitleFont?.let { subtitleSegmentStyle.upperTitleFont = it }
        style.subTitleFont?.let { subtitleSegmentStyle.subTitleFont = it }
        style.titleFontChecked?.let { subtitleSegmentStyle.titleFontChecked = it }
        style.subTitleFontChecked?.let { subtitleSegmentStyle.subTitleFontChecked = it }
        style.upperTitleFontChecked?.let { subtitleSegmentStyle.upperTitleFontChecked = it }

        initWithItems()
    }

    private val title: String?
    private val subTitle: String?
    private val upperTitle: String?

    private var text: TextView? = null
    private var subText: TextView? = null
    private var upperText: TextView? = null

    private val paddingSmall = 2.0f
    private val paddingLarge = 4.0f

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
    }

    override fun onStateChanged(state: SegmentState) {
        with(subtitleSegmentStyle) {
            isSelected = state.value
            if (state.value) {
                upperText?.typeface = upperTitleFontChecked
                text?.typeface = titleFontChecked
                subText?.typeface = subTitleFontChecked
            } else {
                upperText?.typeface = upperTitleFont
                text?.typeface = titleFont
                subText?.typeface = subTitleFont
            }
        }
    }


    private fun initWithItems() {
        removeAllViews()

        with(subtitleSegmentStyle) {
            upperTitle?.let {
                upperText = TextView(context).apply {
                    text = upperTitle

                    setColorStateListOf(upperTitleTextColor, upperTitleTextColorSelected)

                    layoutParams = LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
                    )
                    gravity = upperTitlePosition.toGravity()

                    setTextSize(TypedValue.COMPLEX_UNIT_PX, upperTitleTextSize.toFloat())

                    when (upperTitleType) {
                        TextType.multiline -> {
                            maxLines = Int.MAX_VALUE
                            ellipsize = null
                        }
                        TextType.truncated -> {
                            maxLines = 1
                            ellipsize = TextUtils.TruncateAt.END
                        }
                    }

                    typeface = upperTitleFont

                    includeFontPadding = false
                    setLineSpacing(
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            6.0f,
                            resources.displayMetrics
                        ), 1.0f
                    )

                    setPadding(paddingStart, paddingTop, paddingRight, paddingSmall.toInt())
                }


                addView(upperText)
            }

            title?.let {
                text = TextView(context).apply {
                    text = title

                    setColorStateListOf(titleTextColor, titleTextColorSelected)

                    layoutParams = LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
                    )
                    gravity = titlePosition.toGravity()

                    setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        subtitleSegmentStyle.textSize.toFloat()
                    )

                    when (titleType) {
                        TextType.multiline -> {
                            maxLines = Int.MAX_VALUE
                            ellipsize = null
                        }
                        TextType.truncated -> {
                            maxLines = 1
                            ellipsize = TextUtils.TruncateAt.END
                        }
                    }

                    typeface = titleFont
                    includeFontPadding = false
                    setLineSpacing(
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            6.0f,
                            resources.displayMetrics
                        ), 1.0f
                    )

                    setPadding(
                        paddingStart,
                        paddingLarge.toInt(),
                        paddingRight,
                        paddingLarge.toInt()
                    )
                }

                addView(text)
            }

            subTitle?.let {
                subText = TextView(context).apply {
                    text = subTitle
                    setColorStateListOf(subTitleTextColor, subTitleTextColorSelected)

                    layoutParams = LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
                    )
                    gravity = subTitlePosition.toGravity()

                    setTextSize(TypedValue.COMPLEX_UNIT_PX, subTitleTextSize.toFloat())

                    when (subTitleType) {
                        TextType.multiline -> {
                            maxLines = Int.MAX_VALUE
                            ellipsize = null
                        }
                        TextType.truncated -> {
                            maxLines = 1
                            ellipsize = TextUtils.TruncateAt.END
                        }
                    }

                    typeface = subTitleFont
                    includeFontPadding = false
                    setLineSpacing(
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            3.0f,
                            resources.displayMetrics
                        ), 1.0f
                    )

                    setPadding(paddingStart, paddingLarge.toInt(), paddingRight, paddingBottom)
                }

                addView(subText)
            }
        }
    }
}