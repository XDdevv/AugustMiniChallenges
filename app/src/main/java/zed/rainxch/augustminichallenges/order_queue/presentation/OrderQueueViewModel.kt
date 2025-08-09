package zed.rainxch.augustminichallenges.order_queue.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class OrderQueueUiState(
    val x: Int = 0,
)

sealed interface OrderQueueActions {
    data object OnStartClick : OrderQueueActions
    data object OnPauseClick : OrderQueueActions
}

class OrderQueueViewModel : ViewModel() {
    private val _state = MutableStateFlow(OrderQueueUiState())
    val state = _state.asStateFlow()

    fun onAction(action: OrderQueueActions) {
        when (action) {
            OrderQueueActions.OnPauseClick -> {

            }
            OrderQueueActions.OnStartClick -> {

            }
        }
    }
}