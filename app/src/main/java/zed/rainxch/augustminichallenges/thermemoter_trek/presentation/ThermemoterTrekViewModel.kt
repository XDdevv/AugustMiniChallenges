package zed.rainxch.augustminichallenges.thermemoter_trek.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.launch
import zed.rainxch.augustminichallenges.thermemoter_trek.presentation.model.Thermemoter

enum class ThermemoterState {
    Idle,
    Running,
}

data class ThermemoterTrekUiState(
    val thermemoterState: ThermemoterState = ThermemoterState.Idle,
    val thermemoters: List<Thermemoter> = emptyList(),
)

sealed interface ThermemoterTrekActions {
    data object OnStartClick : ThermemoterTrekActions
    data object OnResetClick : ThermemoterTrekActions
}

class ThermemoterTrekViewModel : ViewModel() {
    private val _state = MutableStateFlow(ThermemoterTrekUiState())
    val state = _state.asStateFlow()

    fun onAction(action: ThermemoterTrekActions) {
        when (action) {
            ThermemoterTrekActions.OnStartClick -> {
                startTracking()
            }

            ThermemoterTrekActions.OnResetClick -> {
                startTracking()
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun startTracking() {
        _state.update {
            it.copy(
                thermemoters = List(20) { Thermemoter(value = "", filled = false) }.toList(),
                thermemoterState = ThermemoterState.Running
            )
        }

        viewModelScope.launch {
            values.asFlow()
                .filter { it > -50 }
                .map { ((it * 9 / 5) + 32) }
                .map { it.truncate1Decimal().padEnd(5, ' ') }
                .onEach { delay(250) }
                .withIndex()
                .take(20)
                .collect { number ->
                    val updatedList = _state.value.thermemoters.toMutableList()
                    updatedList[number.index] = (Thermemoter(value = number.value, filled = true))
                    _state.update {
                        it.copy(
                            thermemoters = updatedList
                        )
                    }
                }
        }
    }

    companion object {
        val values = listOf(
            -100.0,
            0.0,
            -30.5,
            10.0,
            20.0,
            -60.0,
            35.5,
            -51.3,
            22.0,
            19.8,
            45.0,
            55.1,
            60.2,
            90.0,
            -49.9,
            5.0,
            12.3,
            8.8,
            0.5,
            -2.0,
            30.0,
            35.0,
            27.5,
            18.1,
            15.6,
            11.0,
            17.3,
            33.8,
            41.2,
            -80.0
        )
    }

    fun Double.truncate1Decimal(): String {
        val truncated = (this * 10).toInt() / 10.0
        return String.format("%.1f", truncated)
    }
}