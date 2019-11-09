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
import android.widget.RadioGroup
import android.widget.RadioGroup.LayoutParams.MATCH_PARENT
import android.widget.RadioGroup.LayoutParams.WRAP_CONTENT
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import kotlin.math.roundToInt

class SegmentedButton : RadioGroup, View.OnClickListener {

    // customizable values
    private val spreadType: SegmentSpreadType

    private val textSize: Int
    private val segmentHeight: Int

    private val textColor: Int
    private val textColorSelected: Int
    private var segmentFont: Typeface? = null
    private var segmentFontChecked: Typeface? = null

    private val borderColor: Int
    private val borderWidth: Int
    private val borderInnerWidth: Int
    private val r: Float
    private val rI: Float // TODO check if it fixes issue with wrong inner drawable radius

    private val segmentColor: Int
    private val segmentColorSelected: Int

    private val rippleColor: Int
    private val rippleColorSelected: Int

    // inner values
    private val r0 = 0.1f.toPx()

    private val stateChecked = intArrayOf(android.R.attr.state_checked)
    private val stateUnchecked = intArrayOf(-android.R.attr.state_checked)

    private var checkedIndex: Int? = null
    private var checkedChild: Segment? = null

    private var onSegmentSelected: ((segment: Segment) -> Unit)? = null
    private var onSegmentReselected: ((segment: Segment) -> Unit)? = null
    private var onSegmentUnselected: ((segment: Segment) -> Unit)? = null

    var initialCheckedIndex: Int? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SegmentedButton,
            0, 0
        )

        with(a) {

            // TODO add orientation handling
            spreadType =
                SegmentSpreadType.values()[getInt(
                    R.styleable.SegmentedButton_spreadType,
                    SegmentSpreadType.wrap.value
                )]

            textSize = getDimensionPixelSize(
                R.styleable.SegmentedButton_textSize,
                context.resources.getDimensionPixelSize(R.dimen.default_segment_text_size)
            )

            segmentHeight = getDimensionPixelSize(
                R.styleable.SegmentedButton_segmentHeight,
                context.resources.getDimensionPixelSize(R.dimen.default_segment_height)
            )

            textColor = getColor(
                R.styleable.SegmentedButton_textColor,
                ContextCompat.getColor(context, R.color.default_text_color)
            )

            textColorSelected = getColor(
                R.styleable.SegmentedButton_textColorChecked,
                ContextCompat.getColor(context, R.color.default_text_color_checked)
            )

            segmentColor = getColor(
                R.styleable.SegmentedButton_segmentColor,
                ContextCompat.getColor(context, R.color.default_segment_color)
            )

            segmentColorSelected = getColor(
                R.styleable.SegmentedButton_segmentColorChecked,
                ContextCompat.getColor(
                    context,
                    R.color.default_segment_color_checked
                )
            )

            borderColor = getColor(
                R.styleable.SegmentedButton_borderColor,
                ContextCompat.getColor(context, R.color.default_border_color)
            )

            borderWidth = getDimensionPixelSize(
                R.styleable.SegmentedButton_borderWidth,
                context.resources.getDimensionPixelSize(R.dimen.default_border_width)
            )

            r = getDimensionPixelSize(
                R.styleable.SegmentedButton_cornerRadius,
                context.resources.getDimensionPixelSize(R.dimen.default_corner_radius)
            ).toFloat()

            rippleColor = getColor(
                R.styleable.SegmentedButton_rippleColor,
                ContextCompat.getColor(context, R.color.default_ripple_color)
            )

            rippleColorSelected = getColor(
                R.styleable.SegmentedButton_rippleColorChecked,
                ContextCompat.getColor(context, R.color.default_ripple_color_checked)
            )

            borderInnerWidth = (borderWidth / 2f).roundToInt()
            rI = r - borderInnerWidth

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

    override fun onFinishInflate() {
        super.onFinishInflate()
        styleChildSegments(true)
    }

    override fun onClick(v: View?) {
        (v as? Segment)?.let { selected ->
            val indexOf = indexOfChild(v)

            if (checkedIndex == indexOf) {
                onSegmentReselected?.invoke(selected)
            } else {
                selected.typeface = segmentFontChecked
                onSegmentSelected?.invoke(selected)
                checkedChild?.let { unselected ->
                    unselected.typeface = segmentFont
                    onSegmentUnselected?.invoke(unselected)
                }
                checkedChild = selected
                checkedIndex = indexOf
            }
        }
    }

    private fun styleChildSegments(includeRipple: Boolean = true) {
        for (i in 0..childCount) {
            (getChildAt(i) as? RadioButton)?.apply {
                setOnClickListener(this@SegmentedButton)
                initSegment(
                    if (childCount == 1)
                        SegmentType.only
                    else
                        when (i + 1) {
                            1 -> SegmentType.first
                            childCount -> SegmentType.last
                            else -> SegmentType.center
                        },
                    spreadType,
                    includeRipple
                )
            }
        }
    }

    private fun RadioButton.initSegment(
        type: SegmentType,
        spreadType: SegmentSpreadType,
        includeRipple: Boolean
    ): RadioButton {
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, this@SegmentedButton.textSize.toFloat())
        this.typeface = segmentFont

        val state = buildSegmentStateDrawable(
            type,
            segmentColor,
            segmentColorSelected,
            borderColor
        )

        val rippleState = wrapSegmentStateDrawableWithRipple(
            state,
            rippleColor,
            rippleColorSelected
        )

        val textState = buildTextColorStateList(textColor, textColorSelected)

        setTextColor(textState)

        background = if (includeRipple) rippleState else state
        buttonDrawable = null
        gravity = CENTER
        layoutParams = LayoutParams(
            when (spreadType) {
                SegmentSpreadType.evenly -> MATCH_PARENT
                SegmentSpreadType.wrap -> WRAP_CONTENT
            }, segmentHeight, 1f
        )

        return this
    }

    private fun wrapSegmentStateDrawableWithRipple(
        drawable: Drawable,
        rippleColor: Int,
        rippleColorSelected: Int
    ) = RippleDrawable(
        ColorStateList(
            arrayOf(
                stateUnchecked,
                stateChecked
            ), intArrayOf(rippleColor, rippleColorSelected)
        ), drawable, drawable
    )

    private fun buildSegmentStateDrawable(
        type: SegmentType,
        segmentColor: Int,
        segmentColorSelected: Int,
        borderColor: Int,
        borderColorSelected: Int = segmentColorSelected
    ) = StateListDrawable().apply {
        addState(
            stateChecked,
            buildSegmentLayer(type, segmentColorSelected, borderColorSelected)
        )
        addState(
            stateUnchecked,
            buildSegmentLayer(type, segmentColor, borderColor)
        )
    }

    private fun buildTextColorStateList(color: Int, colorSelected: Int) = ColorStateList(
        arrayOf(
            stateUnchecked,
            stateChecked
        ), intArrayOf(color, colorSelected)
    )

    private fun buildShape(type: SegmentType, color: Int, r: Float, r0: Float) = ShapeDrawable().apply {
        shape = RoundRectShape(
            when (type) {
                SegmentType.first -> floatArrayOf(r, r, r0, r0, r0, r0, r, r)
                SegmentType.center -> floatArrayOf(r0, r0, r0, r0, r0, r0, r0, r0)
                SegmentType.last -> floatArrayOf(r0, r0, r, r, r, r, r0, r0)
                SegmentType.only -> floatArrayOf(r, r, r, r, r, r, r, r)
            }, null, null
        )

        paint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            this.color = color
        }
    }

    private fun buildSegmentShape(type: SegmentType, color: Int) = buildShape(type, color, rI, r0)

    private fun buildSegmentStroke(type: SegmentType, color: Int) = buildShape(type, color, r, r0)

    private fun buildSegmentLayer(type: SegmentType, segmentColor: Int, strokeColor: Int = segmentColor) =
        LayerDrawable(
            arrayOf(
                buildSegmentStroke(type, strokeColor),
                buildSegmentShape(type, segmentColor)
            )
        ).apply {
            when (type) {
                SegmentType.first -> setLayerInset(1, borderWidth, borderWidth, borderInnerWidth, borderWidth)
                SegmentType.center -> setLayerInset(1, borderInnerWidth, borderWidth, borderInnerWidth, borderWidth)
                SegmentType.last -> setLayerInset(1, borderInnerWidth, borderWidth, borderWidth, borderWidth)
                SegmentType.only -> setLayerInset(1, borderWidth, borderWidth, borderWidth, borderWidth)

            }
        }

    // move initial checked item initialization to the invoke block in order fo make it order-independent
    private fun setInitialCheckedItem() {
        initialCheckedIndex?.let {

            // reset previously checked segment font to unchecked state
            resetPreviousSegmentFontStateIfExists(checkedIndex)

            checkedIndex = it
            checkedChild = getChildAt(it) as? RadioButton
            checkedChild?.isChecked = true
            checkedChild?.typeface = segmentFontChecked
        }
    }

    private fun resetPreviousSegmentFontStateIfExists(selectedIndex: Int?) {
        selectedIndex?.let {
            (getChildAt(it) as? RadioButton)?.apply {
                typeface = segmentFont
            }
        }
    }

    operator fun invoke(block: SegmentedButton.() -> Unit) = run {
        block()
        setInitialCheckedItem()
    }

    fun onSegmentChecked(block: SegmentedButton.(segment: Segment) -> Unit) {
        onSegmentSelected = { block(it) }
    }

    fun onSegmentRechecked(block: SegmentedButton.(segment: Segment) -> Unit) {
        onSegmentReselected = { block(it) }
    }

    fun onSegmentUnchecked(block: SegmentedButton.(segment: Segment) -> Unit) {
        onSegmentUnselected = { block(it) }
    }

    fun initWithItems(block: SegmentedButton.() -> List<String>) {
        // removing all views in order to prevent duplications which was mentioned by some of the developers
        removeAllViews()
        block().forEach {
            val segment = RadioButton(context)
            segment.text = it
            addView(segment)
        }
        onFinishInflate()
    }
}