package com.creageek.segmentedsample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        segmented {

            // set initial checked segment (null by default)
            initialCheckedIndex = 0

            // init with segments programmatically without RadioButton as a child in xml
            initWithItems {
                // takes only list of strings
                listOf("Today", "This week", "This month")
            }

            // notifies when segment was checked
            onSegmentChecked { segment ->
                Log.d("creageek:segmented", "TextSegment ${segment.text} checked")
            }
            // notifies when segment was unchecked
            onSegmentUnchecked { segment ->
                Log.d("creageek:segmented", "TextSegment ${segment.text} unchecked")
            }
            // notifies when segment was rechecked
            onSegmentRechecked { segment ->
                Log.d("creageek:segmented", "TextSegment ${segment.text} rechecked")
            }
        }

        courses {
            // set initial checked segment (null by default)
            initialCheckedIndex = 0
        }

        filter {
            // set initial checked segment (null by default)
            initialCheckedIndex = 2
        }

        rounded {
            // set initial checked segment (null by default)
            initialCheckedIndex = 2
        }

        wrapped {
            // set initial checked segment (null by default)
            initialCheckedIndex = 1
        }

        subtitled {
            initialCheckedIndex = 0
        }
    }
}
