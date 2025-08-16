package zed.rainxch.augustminichallenges.order_queue.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import zed.rainxch.augustminichallenges.ui.theme.OrderQueueColors

@Composable
fun OrderQueueScreenRoot() {
    val viewModel = viewModel<OrderQueueViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    OrderQueueScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun OrderQueueScreen(
    state: OrderQueueUiState,
    onAction: (OrderQueueActions) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(OrderQueueColors.surface),
        contentAlignment = Alignment.Center
    ) {

    }
}

@Preview
@Composable
private fun OrderQueueScreenPreview() {
    OrderQueueScreen(
        state = OrderQueueUiState(),
        onAction = { }
    )
}