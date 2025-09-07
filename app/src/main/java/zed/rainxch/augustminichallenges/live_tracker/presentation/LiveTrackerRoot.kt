package zed.rainxch.augustminichallenges.live_tracker.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices.FOLDABLE
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import zed.rainxch.augustminichallenges.R
import zed.rainxch.augustminichallenges.live_tracker.presentation.components.LiveTrackerButton
import zed.rainxch.augustminichallenges.live_tracker.presentation.components.LiveTrackerItem
import zed.rainxch.augustminichallenges.live_tracker.presentation.components.LiveTrackerTextChip
import zed.rainxch.augustminichallenges.ui.theme.AugustMiniChallengesTheme
import zed.rainxch.augustminichallenges.ui.theme.LiveTrackerColors
import zed.rainxch.augustminichallenges.ui.theme.hostGrotesk

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
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LiveTrackerColors.surface,
        bottomBar = {
            LiveTrackerBottomBar(
                status = state.status,
                onPauseClick = {
                    onAction(LiveTrackerAction.OnPauseClick)
                },
                onResumeClick = {
                    onAction(LiveTrackerAction.OnResumeClick)
                },
                onBreakXETRAClick = {
                    onAction(LiveTrackerAction.OnBreakXETRAClick)
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Live-Ticker",
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = hostGrotesk,
                        color = LiveTrackerColors.textPrimary,
                        fontSize = 28.sp
                    )

                    LiveTrackerTextChip(
                        text = "Tick Rate: ${state.tickerRate}"
                    )
                }

                Spacer(Modifier.height(12.dp))
            }

            items(
                items = state.items.filter { it.isFeedDown },
                key = { it.id }
            ) { item ->
                LiveTrackerItem(
                    item = item,
                    status = state.status,
                )
            }

            items(
                items = state.items.filter { !it.isFeedDown },
                key = { it.id },
            ) { item ->
                LiveTrackerItem(
                    item = item,
                    status = state.status,
                )
            }
        }
    }
}

@Composable
fun LiveTrackerBottomBar(
    status: LiveTrackerStatus,
    onPauseClick: () -> Unit,
    onResumeClick: () -> Unit,
    onBreakXETRAClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                )
            )
            .background(LiveTrackerColors.surfaceHigher)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {
        LiveTrackerButton(
            text = status.text,
            onClick = {
                when (status) {
                    LiveTrackerStatus.RUNNING -> onPauseClick()
                    LiveTrackerStatus.PAUSED -> onResumeClick()
                }
            },
            icon = when (status) {
                LiveTrackerStatus.RUNNING -> ImageVector.vectorResource(R.drawable.pause_circle)
                LiveTrackerStatus.PAUSED -> ImageVector.vectorResource(R.drawable.ic_play)
            },
            containerColor = LiveTrackerColors.surfaceHighest,
            contentColor = LiveTrackerColors.textPrimary,
            modifier = Modifier.weight(1f)
        )

        LiveTrackerButton(
            text = "Break XETRA",
            onClick = onBreakXETRAClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
@Preview(widthDp = 900, heightDp = 400)
@Composable
private fun Preview() {
    AugustMiniChallengesTheme {
        LiveTrackerScreen(
            state = LiveTrackerState(),
            onAction = {}
        )
    }
}