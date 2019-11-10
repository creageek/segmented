package com.creageek.segmentedbutton

interface Segment {

    fun onStateChanged(state: SegmentState)
}

enum class SegmentState(val value:Boolean){
    Selected(true), Unselected(false)
}