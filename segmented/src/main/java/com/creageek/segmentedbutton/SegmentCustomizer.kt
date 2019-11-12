package com.creageek.segmentedbutton

import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RadioGroup
import com.creageek.segmentedbutton.style.StripStyle

class SegmentCustomizer(private val style: StripStyle) {

    fun onTextSegment(
        view: TextSegment,
        type: SegmentType,
        spreadType: SegmentSpreadType,
        includeRipple: Boolean
    ) = view.apply {
        with(style) {

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

            background = if (includeRipple) rippleState else state
            buttonDrawable = null

            gravity = Gravity.CENTER
            minimumHeight = segmentHeight

            layoutParams = RadioGroup.LayoutParams(
                when (spreadType) {
                    SegmentSpreadType.evenly -> RadioGroup.LayoutParams.MATCH_PARENT
                    SegmentSpreadType.wrap -> RadioGroup.LayoutParams.WRAP_CONTENT
                }, RadioGroup.LayoutParams.MATCH_PARENT, 1f
            )
        }
    }


    fun onSubtitleSegment(
        view: SubtitleSegment,
        type: SegmentType,
        spreadType: SegmentSpreadType,
        includeRipple: Boolean
    ) = view.apply {
        with(style) {

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

            background = if (includeRipple) rippleState else state

            gravity = segmentGravity.toGravity()
            minimumHeight = segmentHeight

            layoutParams = RadioGroup.LayoutParams(
                when (spreadType) {
                    SegmentSpreadType.evenly -> RadioGroup.LayoutParams.MATCH_PARENT
                    SegmentSpreadType.wrap -> RadioGroup.LayoutParams.WRAP_CONTENT
                }, RadioGroup.LayoutParams.MATCH_PARENT, 1f
            ).apply { orientation = LinearLayout.VERTICAL }
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
            with(style) {
                when (type) {
                    SegmentType.first -> setLayerInset(
                        1,
                        borderWidth,
                        borderWidth,
                        borderInnerWidth,
                        borderWidth
                    )
                    SegmentType.center -> setLayerInset(
                        1,
                        borderInnerWidth,
                        borderWidth,
                        borderInnerWidth,
                        borderWidth
                    )
                    SegmentType.last -> setLayerInset(
                        1,
                        borderInnerWidth,
                        borderWidth,
                        borderWidth,
                        borderWidth
                    )
                    SegmentType.only -> setLayerInset(
                        1,
                        borderWidth,
                        borderWidth,
                        borderWidth,
                        borderWidth
                    )
                }
            }
        }
}