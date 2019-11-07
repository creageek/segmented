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
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class SegmentedButton : RadioGroup, View.OnClickListener {

    // customizable style values
    private lateinit var segmentStyle: SegmentStyle

    private val segmentGenerator by lazy { SegmentGenerator(segmentStyle) }

    // inner values
    private var checkedIndex: Int? = null
    private var checkedChild: View? = null

    private var onSegmentSelected: SegmentAction? = null
    private var onSegmentReselected: SegmentAction? = null
    private var onSegmentUnselected: SegmentAction? = null

    var initialCheckedIndex: Int? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        segmentStyle = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SegmentedButton,
            0, 0
        ).toSegmentStyle(context)

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
//        segments = convertClildrenToSegments(true)
        styleChildSegments(true)
    }

    override fun onClick(v: View?) {
        v?.let { selected ->
            val indexOf = indexOfChild(selected)

            if (checkedIndex == indexOf) {
                onSegmentReselected?.invoke(selected)
            } else {
                when (selected) {
                    is RadioButton -> selected.typeface = segmentStyle.segmentFontChecked
                    is TextView -> selected.typeface = segmentStyle.segmentFontChecked
                    is ViewGroup -> {
                        if(selected.childCount>0)
                        for(i in 0 until selected.childCount){
                            selected.getChildAt(i).isSelected = true
                        }
                    }
                }


                selected.isSelected = true

                onSegmentSelected?.invoke(selected)
                checkedChild?.let { unselected ->
                    when (unselected) {
                        is RadioButton -> unselected.typeface = segmentStyle.segmentFont
                        is TextView -> unselected.typeface = segmentStyle.segmentFont
                        is ViewGroup -> {
                            if(unselected.childCount>0)
                                for(i in 0 until unselected.childCount){
                                    unselected.getChildAt(i).isSelected = false
                                }
                        }
                    }

                    unselected.isSelected = false

                    onSegmentUnselected?.invoke(unselected)
                }
                checkedChild = selected
                checkedIndex = indexOf
            }
        }
    }

    private fun convertClildrenToSegments(includeRipple: Boolean = true): MutableList<Segment> {
        val segments = mutableListOf<Segment>()

        for (i in 0..childCount) {
            getChildAt(i)?.apply {
                segments.add(
                    Segment(
                        view = this, type = when (this) {
                            is RadioButton -> ViewType.Radio
                            is TextView -> ViewType.Text
                            is ImageView -> ViewType.Image
                            else -> ViewType.Custom
                        },
                        style = segmentStyle
                    )
                )
            }
        }

        return segments
    }

    private fun styleChildSegments(includeRipple: Boolean = true) {
        for (i in 0..childCount) {
            getChildAt(i)?.apply {
                segmentGenerator.generateSegment(
                    this,
                    if (childCount == 1)
                        SegmentType.only
                    else
                        when (i + 1) {
                            1 -> SegmentType.first
                            childCount -> SegmentType.last
                            else -> SegmentType.center
                        }
                )
                setOnClickListener(this@SegmentedButton)
            }
        }
    }

    // move initial checked item initialization to the invoke block in order fo make it order-independent
    private fun setInitialCheckedItem() {
        initialCheckedIndex?.let {

            // reset previously checked segment font to unchecked state
            resetPreviousSegmentFontStateIfExists(checkedIndex)

            checkedIndex = it
            checkedChild = getChildAt(it)
            when (val child = checkedChild) {
                is RadioButton -> {
                    child.typeface = segmentStyle.segmentFontChecked
                }
                is TextView -> {
                    child.typeface = segmentStyle.segmentFontChecked
                }
            }

            checkedChild?.isSelected = true


        }
    }

    private fun resetPreviousSegmentFontStateIfExists(selectedIndex: Int?) {
        selectedIndex?.let {
            getChildAt(it)?.apply {
                when (this) {
                    is RadioButton -> {
                        typeface = segmentStyle.segmentFont
                    }
                    is TextView -> {
                        typeface = segmentStyle.segmentFont
                    }
                }
            }
        }
    }

    operator fun invoke(block: SegmentedButton.() -> Unit) = run {
        block()
        setInitialCheckedItem()
    }

    fun onSegmentChecked(block: SegmentedButton.(segment: View) -> Unit) {
        onSegmentSelected = { block(it) }
    }

    fun onSegmentRechecked(block: SegmentedButton.(segment: View) -> Unit) {
        onSegmentReselected = { block(it) }
    }

    fun onSegmentUnchecked(block: SegmentedButton.(segment: View) -> Unit) {
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