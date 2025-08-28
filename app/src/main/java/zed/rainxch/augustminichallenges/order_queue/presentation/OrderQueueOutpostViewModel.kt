package zed.rainxch.augustminichallenges.order_queue.presentation

import zed.rainxch.augustminichallenges.order_queue.presentation.model.OrderQueueOutpostAction
import zed.rainxch.augustminichallenges.order_queue.presentation.model.OrderQueueOutpostState
import zed.rainxch.augustminichallenges.order_queue.presentation.model.OrderQueueState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.random.nextLong

class OrderQueueOutpostViewModel : ViewModel() {

    private var consumerJob: Job? = null
    private var producerJob: Job? = null

    private val _state = MutableStateFlow(OrderQueueOutpostState())
    val state = _state.asStateFlow()

    private var orders: Channel<List<Int>> = Channel(
        capacity = 25,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    fun onAction(action: OrderQueueOutpostAction) {
        when (action) {
            OrderQueueOutpostAction.OnStartClick -> startProcessing()
            OrderQueueOutpostAction.OnPauseClick -> pauseProcessing()
        }
    }

    private fun startProcessing() {
        _state.update { it.copy(state = OrderQueueState.Running) }

        // Producer: adds items to queue (increases progress)
        producerJob = viewModelScope.launch {
            while (true) {
                _state.update {
                    if (it.queueProgress < it.overflowQueueSize) {
                        it.copy(
                            queueProgress = it.queueProgress + 1,
                            progressPercentage = ((it.queueProgress.toFloat() / (it.maxQueueSize.toFloat() - 1f)) * 100)
                                .coerceAtMost(120f)
                        )
                    } else it
                }
                delay(Random.nextLong(150L..250L))
            }
        }

        // Consumer: processes items from queue (decreases progress)
        consumerJob?.cancel()
        consumerJob = viewModelScope.launch {
            while (true) {
                _state.update {
                    if (it.queueProgress > 0) {
                        it.copy(
                            queueProgress = it.queueProgress - 1,
                            progressPercentage = (it.queueProgress.toFloat() / (it.maxQueueSize.toFloat() - 1f)) * 100
                        )
                    } else it
                }
                delay(250)
            }
        }
    }

    private fun pauseProcessing() {
        _state.update { it.copy(state = OrderQueueState.Paused) }

        consumerJob?.cancel()
    }
}