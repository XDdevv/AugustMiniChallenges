package zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation.components.ParcelButton
import zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation.components.ParcelItem
import zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation.model.ParcelState
import zed.rainxch.augustminichallenges.ui.theme.AugustMiniChallengesTheme
import zed.rainxch.augustminichallenges.ui.theme.ParcelPigeonRaceColors
import zed.rainxch.augustminichallenges.ui.theme.hostGrotesk

@Composable
fun ParcelPigeonRaceRoot(
    viewModel: ParcelPigeonRaceViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ParcelPigeonRaceScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ParcelPigeonRaceScreen(
    state: ParcelPigeonRaceState,
    onAction: (ParcelPigeonRaceAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize(),
        containerColor = ParcelPigeonRaceColors.surface,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 26.dp),
            contentAlignment = Alignment.Center
        ) {
            when (state.parcelState) {
                ParcelState.Idle -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(ParcelPigeonRaceColors.surfaceHigher)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Parcel Pigeon Race",
                            fontSize = 28.sp,
                            fontFamily = hostGrotesk,
                            fontWeight = FontWeight.SemiBold,
                            color = ParcelPigeonRaceColors.textPrimary
                        )

                        Text(
                            text = "Press Run Again to fetch images",
                            color = ParcelPigeonRaceColors.textSecondary,
                            fontWeight = FontWeight.Normal,
                            fontFamily = hostGrotesk,
                            fontSize = 17.sp
                        )

                        Spacer(Modifier.height(32.dp))

                        ParcelButton(
                            text = "Run Loading",
                            onClick = {
                                onAction(ParcelPigeonRaceAction.OnRunLoadingClick)
                            }
                        )
                    }
                }

                ParcelState.Running, ParcelState.Finished -> {
                    Column {
                        Text(
                            text = "Parcel Pigeon Race",
                            fontSize = 28.sp,
                            fontFamily = hostGrotesk,
                            fontWeight = FontWeight.SemiBold,
                            color = ParcelPigeonRaceColors.textPrimary
                        )

                        Spacer(Modifier.height(20.dp))

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.images) { image ->
                                ParcelItem(
                                    parcelImage = image,
                                    onImageLoaded = { measuredTime ->
                                        onAction(
                                            ParcelPigeonRaceAction.OnImageLoaded(
                                                image = image,
                                                measuredTime = measuredTime,
                                            )
                                        )
                                    },
                                    onImageSizeLoaded = { size ->
                                        onAction(
                                            ParcelPigeonRaceAction.OnImageSizeLoaded(
                                                image = image,
                                                size = size,
                                            )
                                        )
                                    }
                                )
                            }

                        }

                        Spacer(Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total Time:",
                                fontSize = 19.sp,
                                fontFamily = hostGrotesk,
                                fontWeight = FontWeight.Medium,
                                color = ParcelPigeonRaceColors.textPrimary
                            )

                            Text(
                                text = state.totalTime ?: "...",
                                color = ParcelPigeonRaceColors.textSecondary,
                                fontSize = 16.sp,
                                fontFamily = hostGrotesk,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(end = 10.dp)
                            )
                        }

                        Spacer(Modifier.height(20.dp))

                        ParcelButton(
                            text = "Run Again",
                            onClick = {
                                onAction(ParcelPigeonRaceAction.OnRunAgainClick)
                            },
                            enabled = state.parcelState == ParcelState.Finished,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
private fun Preview() {
    AugustMiniChallengesTheme {
        ParcelPigeonRaceScreen(
            state = ParcelPigeonRaceState(),
            onAction = {}
        )
    }
}