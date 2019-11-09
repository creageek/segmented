package com.creageek.segmentedbutton

import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup

class SegmentCustomizer(val stripStyle: StripStyle) {

    fun initSegment(
        view: TextSegment,
        type: SegmentType,
        spreadType: SegmentSpreadType,
        includeRipple: Boolean
    ): RadioButton {
        with(view) {

            val state = buildSegmentStateDrawable(
                type,
                stripStyle.segmentColor,
                stripStyle.segmentColorSelected,
                stripStyle.borderColor
            )

            val rippleState = wrapSegmentStateDrawableWithRipple(
                state,
                stripStyle.rippleColor,
                stripStyle.rippleColorSelected
            )

            background = if (includeRipple) rippleState else state
            buttonDrawable = null
            gravity = Gravity.CENTER
            layoutParams = RadioGroup.LayoutParams(
                when (spreadType) {
                    SegmentSpreadType.evenly -> RadioGroup.LayoutParams.MATCH_PARENT
                    SegmentSpreadType.wrap -> RadioGroup.LayoutParams.WRAP_CONTENT
                }, stripStyle.segmentHeight, 1f
            )

            return this
        }
    }


    fun initSubtitleSegment(
        view: SubtitleSegment,
        type: SegmentType,
        spreadType: SegmentSpreadType,
        includeRipple: Boolean
    ): SubtitleSegment {
        with(view) {
            val state = buildSegmentStateDrawable(
                type,
                stripStyle.segmentColor,
                stripStyle.segmentColorSelected,
                stripStyle.borderColor
            )

            val rippleState = wrapSegmentStateDrawableWithRipple(
                state,
                stripStyle.rippleColor,
                stripStyle.rippleColorSelected
            )



            background = if (includeRipple) rippleState else state
            gravity = Gravity.TOP
            layoutParams = RadioGroup.LayoutParams(
                when (spreadType) {
                    SegmentSpreadType.evenly -> RadioGroup.LayoutParams.MATCH_PARENT
                    SegmentSpreadType.wrap -> RadioGroup.LayoutParams.WRAP_CONTENT
                }, stripStyle.segmentHeight, 1f
            ).apply { orientation = LinearLayout.VERTICAL }

            return this
        }
    }

    private fun wrapSegmentStateDrawableWithRipple(
        drawable: Drawable,
        rippleColor: Int,
        rippleColorSelected: Int
    ) = RippleDrawable(
        ColorStateList(
            arrayOf(
                stripStyle.stateUnselected,
                stripStyle.stateSelected
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
            stripStyle.stateSelected,
            buildSegmentLayer(type, segmentColorSelected, borderColorSelected)
        )
        addState(
            stripStyle.stateUnselected,
            buildSegmentLayer(type, segmentColor, borderColor)
        )
    }

    private fun buildTextColorStateList(color: Int, colorSelected: Int) = ColorStateList(
        arrayOf(
            stripStyle.stateUnselected,
            stripStyle.stateSelected
        ), intArrayOf(color, colorSelected)
    )


    private fun buildShape(type: SegmentType, color: Int, r: Float, r0: Float) =
        ShapeDrawable().apply {
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

    private fun buildSegmentShape(type: SegmentType, color: Int) =
        buildShape(type, color, stripStyle.rI, stripStyle.r0)

    private fun buildSegmentStroke(type: SegmentType, color: Int) =
        buildShape(type, color, stripStyle.r, stripStyle.r0)

    private fun buildSegmentLayer(
        type: SegmentType,
        segmentColor: Int,
        strokeColor: Int = segmentColor
    ) =
        LayerDrawable(
            arrayOf(
                buildSegmentStroke(type, strokeColor),
                buildSegmentShape(type, segmentColor)
            )
        ).apply {
            when (type) {
                SegmentType.first -> setLayerInset(
                    1,
                    stripStyle.borderWidth,
                    stripStyle.borderWidth,
                    stripStyle.borderInnerWidth,
                    stripStyle.borderWidth
                )
                SegmentType.center -> setLayerInset(
                    1,
                    stripStyle.borderInnerWidth,
                    stripStyle.borderWidth,
                    stripStyle.borderInnerWidth,
                    stripStyle.borderWidth
                )
                SegmentType.last -> setLayerInset(
                    1,
                    stripStyle.borderInnerWidth,
                    stripStyle.borderWidth,
                    stripStyle.borderWidth,
                    stripStyle.borderWidth
                )
                SegmentType.only -> setLayerInset(
                    1,
                    stripStyle.borderWidth,
                    stripStyle.borderWidth,
                    stripStyle.borderWidth,
                    stripStyle.borderWidth
                )

            }
        }

}