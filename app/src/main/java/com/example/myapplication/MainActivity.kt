package com.example.myapplication

import android.os.Bundle
import android.text.SpannableString
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.ProgressBar
import androidx.compose.foundation.layout.*

import androidx.compose.ui.unit.dp


import androidx.compose.ui.viewinterop.AndroidView



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val stations = listOf(
                Station(name = "START", distance = 0),
                Station(name = "Central Park", distance = 5),
                Station(name = "Times Square", distance = 10),
                Station(name = "Grand Central", distance = 15),
                Station(name = "Empire State Building", distance = 20),
                Station(name = "Brooklyn Bridge", distance = 25),
                Station(name = "Statue of Liberty", distance = 30),
                Station(name = "Battery Park", distance = 35),
                Station(name = "Wall Street", distance = 40),
                Station(name = "Chinatown", distance = 45),
                Station(name = "Soho", distance = 50),
                Station(name = "Greenwich Village", distance = 55),
                Station(name = "Chelsea", distance = 60),
                Station(name = "High Line", distance = 65),
                Station(name = "Hudson Yards", distance = 70)
            )
            var currentStationIndex = remember { mutableStateOf(0) }
            var useMiles = remember { mutableStateOf(false) }

            JourneyApp(
                stations = stations,
                currentStationIndex = currentStationIndex.value,
                useMiles = useMiles.value,
                onUnitToggle = { useMiles.value = !useMiles.value },
                onNext = {
                    if (currentStationIndex.value < stations.size - 1) {
                        currentStationIndex.value++
                    }
                },
                onPrev = {
                    if (currentStationIndex.value > 0) {
                        currentStationIndex.value--
                    }
                }
            )
        }
    }
}

@Composable
fun MyCustomTextView(customText: String) {
    Text(text = customText)
}

@Composable
fun MyApp(customText: String) {
    MyCustomTextView(customText = customText)
}

@Composable
fun StationInfoinKMS(station: Station) {
    Text(text = "${station.name}: ${station.distance} km")
}
@Composable
fun StationInfoinMiles(station: Station) {
    Text(text = "${station.name}: ${station.distance/1.609} miles")
}


@Composable
fun JourneyApp(stations: List<Station>, currentStationIndex: Int, onUnitToggle: () -> Unit, onNext: () -> Unit, onPrev: () -> Unit, useMiles: Boolean) {
    var totalDistanceCovered = 0
    for (i in 0 until currentStationIndex + 1) {
        totalDistanceCovered += stations[i].distance
    }
    val totalDistance = stations.sumOf { it.distance }
    val distanceToFinalStop = totalDistance - totalDistanceCovered
    val progress = totalDistanceCovered.toFloat() / totalDistance

    Column {
        Text(text = "Current Station: ${stations[currentStationIndex].name}")
        Text(text = "Total Distance Covered: ${if (useMiles) totalDistanceCovered / 1.609 else totalDistanceCovered} ${if (useMiles) "miles" else "km"}")
        Text(text = "Distance to Final Stop: ${if (useMiles) distanceToFinalStop / 1.609 else distanceToFinalStop} ${if (useMiles) "miles" else "km"}")
        LinearProgressIndicator(progress = progress)
        Text(text = "Next Stations:")
        Box(modifier = Modifier.height(200.dp)) { // Set a fixed height for the scrollable area
            LazyColumn {
                items(stations.subList(currentStationIndex + 1, stations.size)) { station ->
                    if (useMiles) {
                        StationInfoinMiles(station = station)
                    } else {
                        StationInfoinKMS(station = station)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(onClick = onPrev) {
                Text(text = "Prev")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onUnitToggle) {
                Text(text = if (useMiles) "Switch to KM" else "Switch to Miles")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onNext) {
                Text(text = "Next")
            }
        }
    }
}