package zed.rainxch.augustminichallenges.live_tracker.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import zed.rainxch.augustminichallenges.ui.theme.AugustMiniChallengesTheme

@Composable
fun LiveTrackerRoot(
    viewModel: LiveTrackerViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LiveTrackerScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun LiveTrackerScreen(
    state: LiveTrackerState,
    onAction: (LiveTrackerAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    AugustMiniChallengesTheme {
        LiveTrackerScreen(
            state = LiveTrackerState(),
            onAction = {}
        )
    }
}