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
import android.view.View
import android.widget.RadioGroup
import com.creageek.segmentedbutton.style.*

class SegmentedButton : RadioGroup, View.OnClickListener {

    var initialCheckedIndex: Int? = null

    // inner values
    private val customizer by lazy { SegmentCustomizer(stripStyle) }

    private val stripStyle: StripStyle
    private var textSegmentStyle: InternalTextSegmentStyle = InternalTextSegmentStyle()
    private var subtitleSegmentStyle: InternalSubtitleSegmentStyle = InternalSubtitleSegmentStyle()

    private var checkedIndex: Int? = null
    private var checkedChild: Segment? = null

    private var onSegmentSelected: ((segment: Segment) -> Unit)? = null
    private var onSegmentReselected: ((segment: Segment) -> Unit)? = null
    private var onSegmentUnselected: ((segment: Segment) -> Unit)? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        stripStyle = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SegmentedButton,
            0, 0
        ).toStripStyle(context)
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
                selected.onStateChanged(SegmentState.selected)
                onSegmentSelected?.invoke(selected)
                checkedChild?.let { unselected ->
                    unselected.onStateChanged(SegmentState.unselected)
                    onSegmentUnselected?.invoke(unselected)
                }
                checkedChild = selected
                checkedIndex = indexOf
            }
        }
    }

    private fun styleChildSegments(includeRipple: Boolean = true) {
        for (i in 0..childCount) {
            (getChildAt(i))?.apply {
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
                    stripStyle.spreadType,
                    includeRipple
                )
            }
        }
    }

    private fun View.initSegment(
        type: SegmentType,
        spreadType: SegmentSpreadType,
        includeRipple: Boolean
    ): View? = when (this) {
        is TextSegment -> customizer.onTextSegment(
            this,
            textSegmentStyle,
            type,
            spreadType,
            includeRipple
        )
        is SubtitleSegment -> customizer.onSubtitleSegment(
            this,
            subtitleSegmentStyle,
            type,
            spreadType,
            includeRipple
        )
        else -> null
    }


    // move initial checked item initialization to the invoke block in order fo make it order-independent
    private fun setInitialCheckedItem() {
        initialCheckedIndex?.let {

            forceCheckItemAt(it)
        }
    }

    fun forceCheckItemAt(index: Int) {
        // reset previously checked segment font to unchecked state
        resetPreviousSegmentFontStateIfExists(checkedIndex)

        checkedIndex = index
        checkedChild = getChildAt(index) as? Segment
        checkedChild?.onStateChanged(SegmentState.selected)
    }

    private fun resetPreviousSegmentFontStateIfExists(selectedIndex: Int?) {
        selectedIndex?.let {
            (getChildAt(it) as? Segment)?.apply {
                onStateChanged(SegmentState.unselected)
            }
        }
    }

    operator fun invoke(block: SegmentedButton.() -> Unit) = run {
        block()
        styleChildSegments(true)
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
            val segment = TextSegment(context, stripStyle)
            segment.text = it
            addView(segment)
        }
        onFinishInflate()
    }

    fun addSegments(block: SegmentedButton.() -> List<Segment>) {
        // removing all views in order to prevent duplications which was mentioned by some of the developers
        removeAllViews()

        block().forEach {
            when (it) {
                is TextSegment -> {
                    it.attachStripStyle(stripStyle)
                    addView(it)
                }
                is SubtitleSegment -> addView(it)
            }
        }

        onFinishInflate()
    }

    fun customizeStrip(block: InternalStripStyle.() -> Unit) {
        // removing all views in order to prevent duplications which was mentioned by some of the developers
        val st = stripStyle.toInternalStyle()
        block(st)
        st.segmentGravity?.let { stripStyle.segmentGravity = it }
        st.spreadType?.let { stripStyle.spreadType = it }
        st.textSize?.let { stripStyle.textSize = it }
        st.segmentHeight?.let { stripStyle.segmentHeight = it }
        st.textColor?.let { stripStyle.textColor = it }
        st.textColorSelected?.let { stripStyle.textColorSelected = it }
        st.segmentFont?.let { stripStyle.segmentFont = it }
        st.segmentFontChecked?.let { stripStyle.segmentFontChecked = it }
        st.borderColor?.let { stripStyle.borderColor = it }
        st.borderWidth?.let { stripStyle.borderWidth = it }
        st.r?.let { stripStyle.r = it }
        st.segmentColor?.let { stripStyle.segmentColor = it }
        st.segmentColorSelected?.let { stripStyle.segmentColorSelected = it }
        st.rippleColor?.let { stripStyle.rippleColor = it }
        st.rippleColorSelected?.let { stripStyle.rippleColorSelected = it }
    }

    fun customizeTextSegment(block: InternalTextSegmentStyle.() -> Unit) {
        // removing all views in order to prevent duplications which was mentioned by some of the developers
        block(textSegmentStyle)
    }

    fun customizeSubtitleSegment(block: InternalSubtitleSegmentStyle.() -> Unit) {
        // removing all views in order to prevent duplications which was mentioned by some of the developers
        block(subtitleSegmentStyle)
    }
}
