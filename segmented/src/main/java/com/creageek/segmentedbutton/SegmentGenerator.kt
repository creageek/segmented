package com.creageek.segmentedbutton

import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*

class SegmentGenerator(private val segmentStyle: SegmentStyle) {

    fun generateSegment(view: View, type: SegmentType): View? = when (view) {
        is RadioButton -> view.initRadioSegment(type, true)
        is TextView -> view.initTextSegment(type, true)
        is ImageView -> view.initImageSegment(type, true)
        else -> view.initCustomViewSegment(type, true)
    }


    private fun RadioButton.initRadioSegment(
        type: SegmentType,
        includeRipple: Boolean
    ): RadioButton {
        this.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            segmentStyle.textSize.toFloat()
        )
        this.typeface = segmentStyle.segmentFont

        val state = buildSegmentStateDrawable(
            type,
            segmentStyle.segmentColor,
            segmentStyle.segmentColorSelected,
            segmentStyle.borderColor
        )

        val rippleState = wrapSegmentStateDrawableWithRipple(
            state,
            segmentStyle.rippleColor,
            segmentStyle.rippleColorSelected
        )

        val textState =
            buildTextColorStateList(segmentStyle.textColor, segmentStyle.textColorSelected)

        setTextColor(textState)

        background = if (includeRipple) rippleState else state
        buttonDrawable = null

        gravity = Gravity.CENTER

        layoutParams = RadioGroup.LayoutParams(
            when (segmentStyle.spreadType) {
                SegmentSpreadType.evenly -> RadioGroup.LayoutParams.MATCH_PARENT
                SegmentSpreadType.wrap -> RadioGroup.LayoutParams.WRAP_CONTENT
            }, segmentStyle.segmentHeight, 1f
        )

        return this
    }

    private fun TextView.initTextSegment(
        type: SegmentType,
        includeRipple: Boolean
    ): TextView {
        this.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            segmentStyle.textSize.toFloat()
        )
        this.typeface = segmentStyle.segmentFont

        val state = buildSegmentStateDrawable(
            type,
            segmentStyle.segmentColor,
            segmentStyle.segmentColorSelected,
            segmentStyle.borderColor
        )

        val rippleState = wrapSegmentStateDrawableWithRipple(
            state,
            segmentStyle.rippleColor,
            segmentStyle.rippleColorSelected
        )

        val textState =
            buildTextColorStateList(segmentStyle.textColor, segmentStyle.textColorSelected)

        setTextColor(textState)

        background = if (includeRipple) rippleState else state

        gravity = Gravity.CENTER

        layoutParams = LinearLayout.LayoutParams(
            when (segmentStyle.spreadType) {
                SegmentSpreadType.evenly -> LinearLayout.LayoutParams.MATCH_PARENT
                SegmentSpreadType.wrap -> LinearLayout.LayoutParams.WRAP_CONTENT
            }, segmentStyle.segmentHeight, 1f
        )

        return this
    }

    private fun ImageView.initImageSegment(
        type: SegmentType,
        includeRipple: Boolean
    ): ImageView {

        val state = buildSegmentStateDrawable(
            type,
            segmentStyle.segmentColor,
            segmentStyle.segmentColorSelected,
            segmentStyle.borderColor
        )

        val rippleState = wrapSegmentStateDrawableWithRipple(
            state,
            segmentStyle.rippleColor,
            segmentStyle.rippleColorSelected
        )

        background = if (includeRipple) rippleState else state

        layoutParams = LinearLayout.LayoutParams(
            when (segmentStyle.spreadType) {
                SegmentSpreadType.evenly -> LinearLayout.LayoutParams.MATCH_PARENT
                SegmentSpreadType.wrap -> LinearLayout.LayoutParams.WRAP_CONTENT
            }, segmentStyle.segmentHeight, 1f
        )

        return this
    }

    private fun View.initCustomViewSegment(
        type: SegmentType,
        includeRipple: Boolean
    ): View {

        val state = buildSegmentStateDrawable(
            type,
            segmentStyle.segmentColor,
            segmentStyle.segmentColorSelected,
            segmentStyle.borderColor
        )

        val rippleState = wrapSegmentStateDrawableWithRipple(
            state,
            segmentStyle.rippleColor,
            segmentStyle.rippleColorSelected
        )

        background = if (includeRipple) rippleState else state

        layoutParams = LinearLayout.LayoutParams(
            when (segmentStyle.spreadType) {
                SegmentSpreadType.evenly -> LinearLayout.LayoutParams.MATCH_PARENT
                SegmentSpreadType.wrap -> LinearLayout.LayoutParams.WRAP_CONTENT
            }, segmentStyle.segmentHeight, 1f
        )

        return this
    }


    private fun buildSegmentStateDrawable(
        type: SegmentType,
        segmentColor: Int,
        segmentColorSelected: Int,
        borderColor: Int,
        borderColorSelected: Int = segmentColorSelected
    ) = StateListDrawable().apply {
        addState(
            segmentStyle.stateSelected,
            buildSegmentLayer(type, segmentColorSelected, borderColorSelected)
        )
        addState(
            segmentStyle.stateUnselected,
            buildSegmentLayer(type, segmentColor, borderColor)
        )
    }

    private fun wrapSegmentStateDrawableWithRipple(
        drawable: Drawable,
        rippleColor: Int,
        rippleColorSelected: Int
    ) = RippleDrawable(
        ColorStateList(
            arrayOf(

                segmentStyle.stateUnselected,
                segmentStyle.stateSelected
            ), intArrayOf(rippleColor, rippleColorSelected)
        ), drawable, drawable
    )

    private fun buildTextColorStateList(color: Int, colorSelected: Int) = ColorStateList(
        arrayOf(

            segmentStyle.stateUnselected,
            segmentStyle.stateSelected
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
        buildShape(type, color, segmentStyle.rI, segmentStyle.r0)

    private fun buildSegmentStroke(type: SegmentType, color: Int) =
        buildShape(type, color, segmentStyle.r, segmentStyle.r0)

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
                    segmentStyle.borderWidth,
                    segmentStyle.borderWidth,
                    segmentStyle.borderInnerWidth,
                    segmentStyle.borderWidth
                )
                SegmentType.center -> setLayerInset(
                    1,
                    segmentStyle.borderInnerWidth,
                    segmentStyle.borderWidth,
                    segmentStyle.borderInnerWidth,
                    segmentStyle.borderWidth
                )
                SegmentType.last -> setLayerInset(
                    1,
                    segmentStyle.borderInnerWidth,
                    segmentStyle.borderWidth,
                    segmentStyle.borderWidth,
                    segmentStyle.borderWidth
                )
                SegmentType.only -> setLayerInset(
                    1,
                    segmentStyle.borderWidth,
                    segmentStyle.borderWidth,
                    segmentStyle.borderWidth,
                    segmentStyle.borderWidth
                )

            }
        }


}