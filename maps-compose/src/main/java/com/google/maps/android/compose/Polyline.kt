// Copyright 2021 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.maps.android.compose

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.Stable
import androidx.compose.runtime.currentComposer
import com.google.android.gms.maps.model.ButtCap
import com.google.android.gms.maps.model.Cap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PatternItem
import com.google.android.gms.maps.model.Polyline
import com.google.maps.android.ktx.addPolyline

@Stable
internal data class PolylineNode(
    val polyline: Polyline,
    var onPolylineClick: (Polyline) -> Unit
)

@Composable
fun GoogleMapScope.Polyline(
    points: List<LatLng>,
    clickable: Boolean = false,
    @ColorInt color: Int = Color.BLACK,
    endCap: Cap = ButtCap(),
    geodesic: Boolean = false,
    jointType: Int = JointType.DEFAULT,
    pattern: List<PatternItem>? = null,
    startCap: Cap = ButtCap(),
    visible: Boolean = true,
    width: Float = 10f,
    zIndex: Float = 0f,
    onClick: (Polyline) -> Unit = {}
) {
    if (currentComposer.applier !is MapApplier) error("Invalid Applier.")
    val mapApplier = currentComposer.applier as MapApplier
    ComposeNode<PolylineNode, MapApplier>(
        factory = {
            val polyline = mapApplier.map.addPolyline {
                addAll(points)
                clickable(clickable)
                color(color)
                endCap(endCap)
                geodesic(geodesic)
                jointType(jointType)
                pattern(pattern)
                startCap(startCap)
                visible(visible)
                width(width)
                zIndex(zIndex)
            }
            PolylineNode(polyline, onClick)
        },
        update = {
            set(onClick) { this.onPolylineClick = it }

            set(points) { this.polyline.points = it }
            set(clickable) { this.polyline.isClickable = it }
            set(color) { this.polyline.color = it }
            set(endCap) { this.polyline.endCap = it }
            set(geodesic) { this.polyline.isGeodesic = it }
            set(jointType) { this.polyline.jointType = it }
            set(pattern) { this.polyline.pattern = it }
            set(startCap) { this.polyline.startCap = it }
            set(visible) { this.polyline.isVisible = it }
            set(width) { this.polyline.width = it }
            set(zIndex) { this.polyline.zIndex = it }
        }
    )
}