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

class SubtitleSegment : LinearLayout, Segment {

    private val subtitleSegmentStyle: SubtitleSegmentStyle

    private val title: String?
    private val subTitle: String?
    private val upperTitle: String?

    private var text: TextView? = null
    private var subText: TextView? = null
    private var upperText: TextView? = null

    private val paddingSmall by lazy {
        //        TypedValue.applyDimension(
//            TypedValue.COMPLEX_UNIT_DIP,
        2.0f
//            resources.displayMetrics
//        )
    }

    private val paddingLarge by lazy {
        //        TypedValue.applyDimension(
//            TypedValue.COMPLEX_UNIT_DIP,
        4.0f
//            resources.displayMetrics
//        )
    }

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
//                    setLineSpacing(0f,0f)
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

//                    setLineSpacing(0f,0f)
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

//                    setLineSpacing(0f,0f)
                    setPadding(paddingStart, paddingLarge.toInt(), paddingRight, paddingBottom)

                }

                addView(subText)
            }
        }
    }
}