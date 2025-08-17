package zed.rainxch.augustminichallenges.heartbeat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import zed.rainxch.augustminichallenges.Application
import zed.rainxch.augustminichallenges.heartbeat.presentation.model.Station
import zed.rainxch.augustminichallenges.heartbeat.presentation.model.StationStatus
import zed.rainxch.augustminichallenges.heartbeat.presentation.utils.SnackbarController
import zed.rainxch.augustminichallenges.heartbeat.presentation.utils.SnackbarEvent
import kotlin.concurrent.timer
import kotlin.time.Duration.Companion.seconds

class HeartbeatViewModel : ViewModel() {

    private var timerJob: Job? = null

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HeartbeatState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                val stations = listOf(
                    Station(
                        id = 1,
                        title = "Station A",
                        status = StationStatus.Online,
                        heartbeatInterval = 300L
                    ),
                    Station(
                        id = 2,
                        title = "Station B",
                        status = StationStatus.Online,
                        heartbeatInterval = 700L
                    ),
                    Station(
                        id = 3,
                        title = "Station C",
                        status = StationStatus.Online,
                        heartbeatInterval = 1000L
                    )
                )

                _state.update {
                    it.copy(
                        stations = stations
                    )
                }

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HeartbeatState()
        )

    init {
        testing2()
    }

    private fun testing1() {
        viewModelScope.launch {
            delay(1.seconds)
            turnOffStation(_state.value.stations[2])
            delay(4.seconds)
            turnOnStations()
        }
    }

    private fun testing2() {
        viewModelScope.launch {
            delay(1.seconds)
            turnOffStation(_state.value.stations[2])
            delay(8.seconds)
            turnOnStations()
        }
    }

    private fun turnOnStations() {
        var updatedList = _state.value.stations
        val offlineStation = updatedList.find { it.status != StationStatus.Online }
        if (offlineStation != null) {
            viewModelScope.launch {
                SnackbarController.sendEvent(SnackbarEvent(message = "${offlineStation.title} is online"))
            }
        }

        updatedList = updatedList.map { station ->
            station.copy(
                status = StationStatus.Online
            )
        }

        timerJob?.cancel()
        timerJob = null
        _state.update {
            it.copy(
                stations = updatedList,
                offlineStation = null
            )
        }
    }

    private fun turnOffStation(station: Station) {
        val stations = _state.value.stations.toMutableList()
        val targetIndex = stations.indexOfFirst { it.id == station.id }
        val updatedStation = stations[targetIndex].copy(
            status = StationStatus.Offline(0.0)
        )
        stations[targetIndex] = updatedStation

        _state.update {
            it.copy(
                stations = stations,
                offlineStation = updatedStation
            )
        }

        startTimerForStation(updatedStation)
    }

    private fun startTimerForStation(updatedStation: Station) {
        val stations = _state.value.stations.toMutableList()
        val targetIndex = stations.indexOfFirst { it.id == updatedStation.id }


        timerJob?.cancel()
        timerJob = Application.instance.applicationScope.launch {
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = "${updatedStation.title} is offline",
                    isSuccessEvent = false
                )
            )

            val startTime = System.currentTimeMillis()

            while (true) {
                delay(100)

                val endTime = System.currentTimeMillis() - startTime

                val updatedStation = stations[targetIndex].copy(
                    status = StationStatus.Offline(endTime / 1000.0)
                )
                stations[targetIndex] = updatedStation

                _state.update {
                    it.copy(
                        stations = stations,
                        offlineStation = updatedStation
                    )
                }
            }
        }
    }

    fun onAction(action: HeartbeatAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}