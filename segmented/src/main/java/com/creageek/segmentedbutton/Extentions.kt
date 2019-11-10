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
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Typeface
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

inline fun <R> R?.orElse(block: () -> R): R {
    return this ?: block()
}

fun Float.toPx() = (this * Resources.getSystem().displayMetrics.density)

fun TypedArray.getDimensInPixel(context: Context, styleableId: Int, defaultDimen: Int) =
    getDimensionPixelSize(styleableId, context.resources.getDimensionPixelSize(defaultDimen))

fun TypedArray.getColor(context: Context, styleableId: Int, defaultColor: Int) =
    getColor(styleableId, ContextCompat.getColor(context, defaultColor))

fun TypedArray.getFont(
    context: Context,
    styleableId: Int,
    defaultFont: Typeface? = null
): Typeface? {
    val titleFontId = getResourceId(styleableId, -1)
    return if (titleFontId != -1) {
        ResourcesCompat.getFont(context, titleFontId)
    } else {
        defaultFont
    }
}

fun TextView.setColorStateListOf(color: Int, colorSelected: Int) {
    val list = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_selected),
            intArrayOf(android.R.attr.state_selected)
        ), intArrayOf(color, colorSelected)
    )
    setTextColor(list)
}