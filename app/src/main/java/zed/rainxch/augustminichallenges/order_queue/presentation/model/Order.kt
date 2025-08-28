package zed.rainxch.augustminichallenges.order_queue.presentation.model
data class OrderQueueOutpostState(
    val state: OrderQueueState = OrderQueueState.Idle,
    val queueProgress: Int = 0,
    val maxQueueSize: Int = 25,
    val overflowQueueSize: Int = 30,
    val progressPercentage: Float = 0f,
)

enum class OrderQueueState {
    Idle,
    Running,
    Paused
}

// Simple Actions
sealed interface OrderQueueOutpostAction {
    data object OnStartClick : OrderQueueOutpostAction
    data object OnPauseClick : OrderQueueOutpostAction
}