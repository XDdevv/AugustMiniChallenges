package zed.rainxch.augustminichallenges.order_queue.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

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
    modifier: Modifier = Modifier,
    state: OrderQueueUiState,
    onAction: (OrderQueueActions) -> Unit,
) {

}

@Preview
@Composable
private fun OrderQueueScreenPreview() {
    OrderQueueScreen()
}