package zed.rainxch.augustminichallenges.heartbeat.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import okhttp3.internal.format
import zed.rainxch.augustminichallenges.heartbeat.presentation.components.HeartbeatSnack
import zed.rainxch.augustminichallenges.heartbeat.presentation.components.StationItem
import zed.rainxch.augustminichallenges.heartbeat.presentation.model.StationStatus
import zed.rainxch.augustminichallenges.heartbeat.presentation.utils.ObserveAsEvents
import zed.rainxch.augustminichallenges.heartbeat.presentation.utils.SnackbarController
import zed.rainxch.augustminichallenges.ui.theme.AugustMiniChallengesTheme
import zed.rainxch.augustminichallenges.ui.theme.HeartbeatColors
import zed.rainxch.augustminichallenges.ui.theme.ParcelPigeonRaceColors
import zed.rainxch.augustminichallenges.ui.theme.hostGrotesk
import java.util.Locale

@Composable
fun HeartbeatRoot(
    viewModel: HeartbeatViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    ObserveAsEvents(SnackbarController.events, snackbarHostState) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                message = event.message,
                duration = if (event.isSuccessEvent) SnackbarDuration.Short else SnackbarDuration.Indefinite
            )
        }
    }

    HeartbeatScreen(
        state = state,
        onAction = viewModel::onAction,
        snackbarHostState = snackbarHostState,
    )
}

@Composable
fun HeartbeatScreen(
    state: HeartbeatState,
    onAction: (HeartbeatAction) -> Unit,
    snackbarHostState: SnackbarHostState,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = HeartbeatColors.surface,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                HeartbeatSnack(
                    message = data.visuals.message,
                    containerColor = if (data.visuals.duration == SnackbarDuration.Short) {
                        HeartbeatColors.primary
                    } else HeartbeatColors.error,
                    isSuccess = data.visuals.duration == SnackbarDuration.Short
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Heartbeat Sentinel",
                    fontSize = 28.sp,
                    fontFamily = hostGrotesk,
                    fontWeight = FontWeight.SemiBold,
                    color = ParcelPigeonRaceColors.textPrimary
                )

                Text(
                    text = if (state.offlineStation == null) {
                        "All Stations Operational"
                    } else "${state.offlineStation.title} offline " +
                            String.format(
                                locale = Locale.getDefault(),
                                format = "%.1f s",
                                (state.offlineStation.status as StationStatus.Offline).timeInSecond
                            ),
                    color = ParcelPigeonRaceColors.textSecondary,
                    fontWeight = FontWeight.Normal,
                    fontFamily = hostGrotesk,
                    fontSize = 17.sp
                )

                Spacer(Modifier.height(24.dp))

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(state.stations) { station ->
                        StationItem(
                            station = station
                        )
                    }
                }

                Spacer(Modifier.height(100.dp)) // Placeholder
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AugustMiniChallengesTheme {
        HeartbeatScreen(
            state = HeartbeatState(),
            onAction = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}