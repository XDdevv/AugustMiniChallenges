package zed.rainxch.augustminichallenges.order_queue.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import zed.rainxch.augustminichallenges.ui.theme.AugustMiniChallengesTheme

@Composable
fun OrderQueueOutpostRoot(
    viewModel: OrderQueueOutpostViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    OrderQueueOutpostScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun OrderQueueOutpostScreen(
    state: OrderQueueOutpostState,
    onAction: (OrderQueueOutpostAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    AugustMiniChallengesTheme {
        OrderQueueOutpostScreen(
            state = OrderQueueOutpostState(),
            onAction = {}
        )
    }
}