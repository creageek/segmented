package com.creageek.segmentedbutton

import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup

class SegmentCustomizer(val style: StripStyle) {

    fun initSegment(
        view: TextSegment,
        type: SegmentType,
        spreadType: SegmentSpreadType,
        includeRipple: Boolean
    ): RadioButton {
        with(view) {

            val state = buildSegmentStateDrawable(
                type,
                style.segmentColor,
                style.segmentColorSelected,
                style.borderColor
            )

            val rippleState = wrapSegmentStateDrawableWithRipple(
                state,
                style.rippleColor,
                style.rippleColorSelected
            )

            background = if (includeRipple) rippleState else state
            buttonDrawable = null
            gravity = Gravity.CENTER
            minimumHeight = style.segmentHeight
            layoutParams = RadioGroup.LayoutParams(
                when (spreadType) {
                    SegmentSpreadType.evenly -> RadioGroup.LayoutParams.MATCH_PARENT
                    SegmentSpreadType.wrap -> RadioGroup.LayoutParams.WRAP_CONTENT
                }, RadioGroup.LayoutParams.MATCH_PARENT, 1f
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
                style.segmentColor,
                style.segmentColorSelected,
                style.borderColor
            )

            val rippleState = wrapSegmentStateDrawableWithRipple(
                state,
                style.rippleColor,
                style.rippleColorSelected
            )



            background = if (includeRipple) rippleState else state
            gravity = style.segmentGravity.toGravity()
            minimumHeight = style.segmentHeight
            layoutParams = RadioGroup.LayoutParams(
                when (spreadType) {
                    SegmentSpreadType.evenly -> RadioGroup.LayoutParams.MATCH_PARENT
                    SegmentSpreadType.wrap -> RadioGroup.LayoutParams.WRAP_CONTENT
                }, RadioGroup.LayoutParams.MATCH_PARENT, 1f
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
                style.stateUnselected,
                style.stateSelected
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
            style.stateSelected,
            buildSegmentLayer(type, segmentColorSelected, borderColorSelected)
        )
        addState(
            style.stateUnselected,
            buildSegmentLayer(type, segmentColor, borderColor)
        )
    }

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
        buildShape(type, color, style.rI, style.r0)

    private fun buildSegmentStroke(type: SegmentType, color: Int) =
        buildShape(type, color, style.r, style.r0)

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
                    style.borderWidth,
                    style.borderWidth,
                    style.borderInnerWidth,
                    style.borderWidth
                )
                SegmentType.center -> setLayerInset(
                    1,
                    style.borderInnerWidth,
                    style.borderWidth,
                    style.borderInnerWidth,
                    style.borderWidth
                )
                SegmentType.last -> setLayerInset(
                    1,
                    style.borderInnerWidth,
                    style.borderWidth,
                    style.borderWidth,
                    style.borderWidth
                )
                SegmentType.only -> setLayerInset(
                    1,
                    style.borderWidth,
                    style.borderWidth,
                    style.borderWidth,
                    style.borderWidth
                )

            }
        }
}