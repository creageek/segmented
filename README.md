# segmented
<img src="https://github.com/creageek/segmented/blob/master/graphics/sample.png" width="360" max-width="360px">

A simple Android UI library for segmented controls with material-ish styling.

[![](https://jitpack.io/v/creageek/segmented.svg)](https://jitpack.io/#creageek/segmented) [![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-segmented-green.svg?style=flat )]( https://android-arsenal.com/details/1/7706 )
## How to add?
I. In your root `build.gradle` file add the following:
```
allprojects {
	repositories {
		...
	    maven { url 'https://jitpack.io' }
	}
}
```
II. In your `build.gradle` file add the following dependency:
```
dependencies {
    implementation 'com.github.creageek:segmented:1.0.0'
}
```
III. Declare `SegmentedButton` inside your `layout.xml` file:
```xml
<com.creageek.segmentedbutton.SegmentedButton
           android:id="@+id/segmented"
           android:layout_width="0dp"
           android:layout_height="wrap_content"
           app:layout_constraintTop_toTopOf="parent"
           app:layout_constraintStart_toStartOf="parent"
           app:layout_constraintEnd_toEndOf="parent"/>
```
Please, note that `SegmentedButton` extends from `RadioGroup` so you can add `RadioButton` (doesn't need any additional styling) views as a child to fetch the segments.

IV. Declare `SegmentedButton` in your `class.kt`.
You can call the view by its id with the power of `kotlin-android-extensions` plugin or just use `findViewById<SegmentedButton>(R.id.segmented)`:

```kotlin
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
            Log.d("creageek:segmented", "Segment ${segment.text} checked")
        }
        // notifies when segment was unchecked
        onSegmentUnchecked { segment ->
            Log.d("creageek:segmented", "Segment ${segment.text} unchecked")
        }
        // notifies when segment was rechecked
        onSegmentRechecked { segment ->
            Log.d("creageek:segmented", "Segment ${segment.text} rechecked")
        }
}
```
## How to customize?
I. In your `layout.xml` file you can set a few attrs to `SegmentedButton`:
* `app:textSize` - applies text size to all child segments (reference to a dimension).
* `app:segmentHeight` - allows you to set segment height (reference to a dimension).
* `app:textColor` - allows you to set text color (reference to a color).
* `app:textColorChecked` - allows you to set the text color of a checked segment (reference to a color).
* `app:segmentFont` - allows you to set a segment's font (reference to a font).
* `app:segmentFontChecked` - allows you to set the font of a checked segment (reference to a font).
* `app:segmentColor` - allows you to set segment color (reference to a color).
* `app:segmentColorChecked` - allows you to set the color of a checked segment (reference to a color).
* `app:rippleColor` - allows you to set a segment's ripple color (reference to a color).
* `app:rippleColorChecked` - allows you to set ripple color of a checked segment (reference to a color).
* `app:borderColor` - allows you to set the color of a segment's border (reference to a color).
* `app:borderWidth` - allows you to set a segment's border width (reference to a dimension).
* `app:cornerRadius` - allows you to set a segment's corner radius (reference to a dimension).
* `app:spreadType` - sets the spread type of a `SegmentedButton`. Possible values are: 
  * `wrap` (default)
  * `evenly`
        
II. By default, if no custom attrs set, these values applied:
* Default `colors.xml` values:
```xml
<resources>
    <color name="default_text_color">#3e3e3d</color>
    <color name="default_text_color_checked">#ffffff</color>

    <color name="default_segment_color">#f8f8f8</color>
    <color name="default_segment_color_checked">#7f6a93</color>

    <color name="default_ripple_color">#e4e4e4</color>
    <color name="default_ripple_color_checked">#c695a4</color>

    <color name="default_border_color">#e4e4e4</color>
</resources>
```
* Default `dimens.xml` values:
```xml
<resources>
    <dimen name="compound_drawable_size">24dp</dimen>
    <dimen name="default_segment_text_size">16sp</dimen>
    <dimen name="default_segment_height">48dp</dimen>
    <dimen name="default_border_width">1dp</dimen>
    <dimen name="default_corner_radius">8dp</dimen>
</resources>
```
## What about contributions?
If you have any ideas, suggestions, or issues, please feel free to create the following issue. I'll be here to help and/or improve `SegmentedButton` according to your needs (if it doesn't confront the original idea).
## License
```
Copyright (C) 2019 Ruslan Kishai

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
