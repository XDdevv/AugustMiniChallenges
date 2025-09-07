package zed.rainxch.augustminichallenges.live_tracker.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.util.toRange
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import zed.rainxch.augustminichallenges.live_tracker.presentation.model.DifferenceType
import zed.rainxch.augustminichallenges.live_tracker.presentation.model.TrackItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.seconds

@RequiresApi(Build.VERSION_CODES.O)
class LiveTrackerViewModel : ViewModel() {

    private var hasLoadedInitialData = false
    private var updateJob: Job? = null

    private val _state = MutableStateFlow(LiveTrackerState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadInitialData()

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = LiveTrackerState()
        )

    fun onAction(action: LiveTrackerAction) {
        when (action) {
            LiveTrackerAction.OnBreakXETRAClick -> {
                val list = _state.value.items.toMutableList()

                val index = list.indexOfFirst { it.currency == "XETRA" }

                if (index != -1) {
                    list[index] = list[index].copy(isFeedDown = true)
                }

                _state.update { it.copy(items = list.toImmutableList()) }

                startUnfeedDownJob()
            }

            LiveTrackerAction.OnPauseClick -> {
                stopUpdating()
            }

            LiveTrackerAction.OnResumeClick -> {
                startUpdating()
            }
        }
    }

    private fun startUpdating() {
        _state.update {
            it.copy(
                tickerRate = "4/s",
                status = LiveTrackerStatus.RUNNING,
            )
        }

        updateJob = viewModelScope.launch {
            _state.value.items.forEach { item ->
                launch {
                    while (true) {
                        delay(item.updateDelayMs)

                        updateItem(item)
                    }
                }
            }
        }
    }

    private fun updateItem(item: TrackItem) {
        val list = _state.value.items.toMutableList()
        val index = _state.value.items.indexOfFirst { it.id == item.id }
        if (index != -1 && !list[index].isFeedDown) {
            val randomValue = list[index].value + (-3..5).random()
            val type = when {
                list[index].value > randomValue -> DifferenceType.DECREASE
                list[index].value < randomValue -> DifferenceType.INCREASE
                else -> DifferenceType.EQUAL
            }

            list[index] = list[index].copy(
                value = randomValue,
                differenceType = type,
                latestUpdateTime = getCurrentTimeFormatted()
            )
        }

        _state.update { it.copy(items = list.toImmutableList()) }

    }

    private fun stopUpdating() {
        _state.update {
            it.copy(
                tickerRate = "--",
                status = LiveTrackerStatus.PAUSED,
            )
        }

        updateJob?.cancel()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val feeds = listOf(
                TrackItem(
                    currency = "BSE",
                    latestUpdateTime = getCurrentTimeFormatted(),
                    value = 99.95,
                    differenceType = DifferenceType.EQUAL,
                    updateDelayMs = 2000
                ),
                TrackItem(
                    currency = "HKEX",
                    latestUpdateTime = getCurrentTimeFormatted(),
                    value = 98.00,
                    differenceType = DifferenceType.EQUAL,
                    updateDelayMs = 2000
                ),
                TrackItem(
                    currency = "JPX",
                    latestUpdateTime = getCurrentTimeFormatted(),
                    value = 96.80,
                    differenceType = DifferenceType.EQUAL,
                    updateDelayMs = 4000
                ),
                TrackItem(
                    currency = "LSE",
                    latestUpdateTime = getCurrentTimeFormatted(),
                    value = 105.70,
                    differenceType = DifferenceType.EQUAL,
                    updateDelayMs = 5000
                ),
                TrackItem(
                    currency = "NASDAQ",
                    latestUpdateTime = getCurrentTimeFormatted(),
                    value = 134.10,
                    differenceType = DifferenceType.EQUAL,
                    updateDelayMs = 3000
                ),
                TrackItem(
                    currency = "NYSE",
                    latestUpdateTime = getCurrentTimeFormatted(),
                    value = 118.35,
                    differenceType = DifferenceType.EQUAL,
                    updateDelayMs = 3000
                ),
                TrackItem(
                    currency = "SSE",
                    latestUpdateTime = getCurrentTimeFormatted(),
                    value = 110.25,
                    differenceType = DifferenceType.EQUAL,
                    updateDelayMs = 5000
                ),
                TrackItem(
                    currency = "SIX",
                    latestUpdateTime = getCurrentTimeFormatted(),
                    value = 115.50,
                    differenceType = DifferenceType.EQUAL,
                    updateDelayMs = 4000
                ),
                TrackItem(
                    currency = "TSX",
                    latestUpdateTime = getCurrentTimeFormatted(),
                    value = 122.90,
                    differenceType = DifferenceType.EQUAL,
                    updateDelayMs = 3000
                ),
                TrackItem(
                    currency = "XETRA",
                    latestUpdateTime = getCurrentTimeFormatted(),
                    value = 140.40,
                    differenceType = DifferenceType.EQUAL,
                    updateDelayMs = 1000
                ),
            )

            _state.update {
                it.copy(
                    status = LiveTrackerStatus.RUNNING,
                    items = feeds.toImmutableList(),
                    tickerRate = "4/s"
                )
            }

            startUpdating()
        }
    }

    private fun getCurrentTimeFormatted(): String {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"))
    }

    private fun startUnfeedDownJob() {
        viewModelScope.launch {
            delay(4.seconds)

            val list = _state.value.items.toMutableList()

            val index = list.indexOfFirst { it.currency == "XETRA" }

            if (index != -1) {
                list[index] = list[index].copy(isFeedDown = false)
            }

            _state.update { it.copy(items = list.toImmutableList()) }
        }
    }

}