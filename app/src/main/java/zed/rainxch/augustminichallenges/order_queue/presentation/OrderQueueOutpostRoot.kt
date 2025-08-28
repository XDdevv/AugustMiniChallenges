package zed.rainxch.augustminichallenges.order_queue.presentation

import zed.rainxch.augustminichallenges.order_queue.presentation.model.OrderQueueOutpostAction
import zed.rainxch.augustminichallenges.order_queue.presentation.model.OrderQueueOutpostState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import zed.rainxch.augustminichallenges.R
import zed.rainxch.augustminichallenges.order_queue.presentation.components.OrderQueueProgressbar
import zed.rainxch.augustminichallenges.order_queue.presentation.model.OrderQueueState
import zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation.components.ParcelButton
import zed.rainxch.augustminichallenges.ui.theme.AugustMiniChallengesTheme
import zed.rainxch.augustminichallenges.ui.theme.OrderQueueColors
import zed.rainxch.augustminichallenges.ui.theme.hostGrotesk

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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OrderQueueColors.surface),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(360.dp)
                .clip(RoundedCornerShape(16.dp))
                .dropShadow(
                    shape = RoundedCornerShape(16.dp),
                    shadow = Shadow(
                        radius = 8.dp,
                        color = OrderQueueColors.textPrimary.copy(alpha = .08f)
                    )
                )
                .background(OrderQueueColors.surfaceHigher)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.order_queue_outpost),
                fontFamily = hostGrotesk,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                color = OrderQueueColors.textPrimary
            )

            if (state.state == OrderQueueState.Idle) {
                OrderQueueIdle(
                    onStartClick = {
                        onAction(OrderQueueOutpostAction.OnStartClick)
                    },
                )
            } else {
                OrderQueueActive(
                    state = state,
                    onPauseClick = {
                        onAction(OrderQueueOutpostAction.OnPauseClick)
                    },
                    onStartClick = {
                        onAction(OrderQueueOutpostAction.OnStartClick)
                    }
                )
            }
        }
    }
}

@Composable
fun ColumnScope.OrderQueueActive(
    state: OrderQueueOutpostState,
    onPauseClick: () -> Unit,
    onStartClick: () -> Unit,
) {
    Spacer(Modifier.height(24.dp))

    // Queue progress text
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Queue: ${state.queueProgress}/${state.maxQueueSize}",
            fontFamily = hostGrotesk,
            fontWeight = FontWeight.Medium,
            color = OrderQueueColors.textPrimary,
            fontSize = 16.sp
        )

        Spacer(Modifier.weight(1f))

        val textColor = if (state.progressPercentage < 100) {
            OrderQueueColors.textPrimary
        } else OrderQueueColors.overload

        Text(
            text = String.format("%.0f%%", state.progressPercentage),
            fontFamily = hostGrotesk,
            fontWeight = if (state.progressPercentage < 100) FontWeight.Normal else FontWeight.Medium,
            color = textColor,
            fontSize = 16.sp
        )
    }

    Spacer(Modifier.height(12.dp))

    // Progress bar with color based on percentage
    val progressColor = when {
        state.progressPercentage < 50 -> Color(0xFF4CAF50) // Green
        state.progressPercentage < 80 -> Color(0xFFFF9800) // Orange
        state.progressPercentage < 100 -> Color(0xFFF44336) // Red
        else -> Color(0xFFE91E63) // Pink for overload
    }


    OrderQueueProgressbar(
        progressPercentage = state.queueProgress.toFloat() / state.maxQueueSize,
        progress = state.queueProgress,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp)),
        progressColor = progressColor,
        trackColor = OrderQueueColors.surfaceHighest,
        segments = 3,
        overflow = state.maxQueueSize
    )

    Spacer(Modifier.height(32.dp))

    // Control button
    if (state.state == OrderQueueState.Running) {
        ParcelButton(
            text = "Pause",
            onClick = onPauseClick
        )
    } else {
        ParcelButton(
            text = "Start",
            onClick = onStartClick
        )
    }
}

@Composable
fun ColumnScope.OrderQueueIdle(
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Press Play to start processing orders.",
        fontFamily = hostGrotesk,
        fontWeight = FontWeight.Normal,
        color = OrderQueueColors.textSecondary,
        fontSize = 17.sp
    )

    Spacer(Modifier.height(32.dp))

    ParcelButton(
        text = "▶ Start",
        onClick = onStartClick
    )
}

@Preview
@Composable
private fun PreviewIdle() {
    AugustMiniChallengesTheme {
        OrderQueueOutpostScreen(
            state = OrderQueueOutpostState(),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun PreviewRunning() {
    AugustMiniChallengesTheme {
        OrderQueueOutpostScreen(
            state = OrderQueueOutpostState(
                state = OrderQueueState.Running,
                queueProgress = 16,
                maxQueueSize = 25,
                progressPercentage = 64f
            ),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun PreviewOverload() {
    AugustMiniChallengesTheme {
        OrderQueueOutpostScreen(
            state = OrderQueueOutpostState(
                state = OrderQueueState.Running,
                queueProgress = 30,
                maxQueueSize = 25,
                progressPercentage = 120f
            ),
            onAction = {}
        )
    }
}