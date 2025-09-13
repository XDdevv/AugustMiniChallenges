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
            OrderQueueOutpostAction.OnStartClick -> {
                if (_state.value.state == OrderQueueState.Paused) {
                    resumeProcessing()
                } else {
                    startProcessing()
                }
            }
            OrderQueueOutpostAction.OnPauseClick -> pauseProcessing()
        }
    }

    private fun startProcessing() {
        _state.update { it.copy(state = OrderQueueState.Running) }

        // Cancel any existing jobs
        producerJob?.cancel()
        consumerJob?.cancel()

        startProducer()
        startConsumer()
    }

    private fun resumeProcessing() {
        _state.update { it.copy(state = OrderQueueState.Running) }

        // Only restart the consumer - producer should already be running
        startConsumer()
    }

    private fun pauseProcessing() {
        _state.update { it.copy(state = OrderQueueState.Paused) }

        // Only cancel the consumer - producer continues running
        consumerJob?.cancel()
        consumerJob = null
    }

    private fun startProducer() {
        producerJob = viewModelScope.launch {
            while (true) {
                _state.update { currentState ->
                    if (currentState.queueProgress < currentState.overflowQueueSize) {
                        val newProgress = currentState.queueProgress + 1
                        currentState.copy(
                            queueProgress = newProgress,
                            progressPercentage = calculateProgressPercentage(newProgress, currentState.maxQueueSize)
                        )
                    } else currentState
                }
                delay(250L) // Fixed 250ms delay for producer
            }
        }
    }

    private fun startConsumer() {
        consumerJob = viewModelScope.launch {
            while (true) {
                _state.update { currentState ->
                    if (currentState.queueProgress > 0) {
                        val newProgress = currentState.queueProgress - 1
                        currentState.copy(
                            queueProgress = newProgress,
                            progressPercentage = calculateProgressPercentage(newProgress, currentState.maxQueueSize)
                        )
                    } else currentState
                }
                // Random delay between 100-250ms for consumer (faster than producer)
                delay(Random.nextLong(100L, 251L))
            }
        }
    }

    private fun calculateProgressPercentage(progress: Int, maxQueueSize: Int): Float {
        return if (maxQueueSize > 1) {
            (progress.toFloat() / (maxQueueSize.toFloat() - 1f)) * 100f
        } else {
            0f
        }
    }

    override fun onCleared() {
        super.onCleared()
        producerJob?.cancel()
        consumerJob?.cancel()
    }
}