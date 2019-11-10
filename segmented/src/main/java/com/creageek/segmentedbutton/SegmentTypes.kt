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

import android.view.Gravity

enum class SegmentType {
    first, center, last, only
}

enum class SegmentSpreadType(val value: Int) {
    evenly(0), wrap(1)
}

enum class TextPosition(val value: Int) {
    start(0), center(1), end(2)
}

enum class SegmentGravity(val value: Int) {
    top(0), center(1), bottom(2)
}

fun TextPosition.toGravity() = when (this) {
    TextPosition.start -> {
        Gravity.START
    }
    TextPosition.center -> {
        Gravity.CENTER_HORIZONTAL
    }
    TextPosition.end -> {
        Gravity.END
    }
}

fun SegmentGravity.toGravity() = when (this) {
    SegmentGravity.top -> {
        Gravity.TOP
    }
    SegmentGravity.center -> {
        Gravity.CENTER_VERTICAL
    }
    SegmentGravity.bottom -> {
        Gravity.BOTTOM
    }
}