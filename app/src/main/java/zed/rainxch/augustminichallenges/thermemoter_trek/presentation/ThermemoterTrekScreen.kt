package zed.rainxch.augustminichallenges.thermemoter_trek.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import zed.rainxch.augustminichallenges.R
import zed.rainxch.augustminichallenges.thermemoter_trek.presentation.components.ThermemoterButton
import zed.rainxch.augustminichallenges.ui.theme.ThermemoterColors
import zed.rainxch.augustminichallenges.ui.theme.hostGrotesk

@Composable
fun ThermemoterTrekScreenRoot() {
    val viewModel = viewModel<ThermemoterTrekViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    ThermemoterTrekScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ThermemoterTrekScreen(
    state: ThermemoterTrekUiState,
    onAction: (ThermemoterTrekActions) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(ThermemoterColors.surface),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .width(360.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = ThermemoterColors.shadow
                    )
                    .background(ThermemoterColors.surfaceHigher)
                    .padding(24.dp)
                    .align(Alignment.Center)
                    .animateContentSize(),
                horizontalAlignment = if (state.thermemoterState == ThermemoterState.Idle) {
                    Alignment.CenterHorizontally
                } else Alignment.Start
            ) {
                Text(
                    text = "Thermometer Treck",
                    color = ThermemoterColors.textPrimary,
                    fontFamily = hostGrotesk,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 28.sp,
                )

                Spacer(Modifier.height(4.dp))

                when (state.thermemoterState) {
                    ThermemoterState.Running -> {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                tint = if (state.thermemoters.count { it.filled } == state.thermemoters.size) {
                                    ThermemoterColors.primary
                                } else ThermemoterColors.textDisabled,
                                modifier = Modifier.size(16.dp)
                            )

                            Text(
                                text = "${state.thermemoters.count { it.filled }}/${state.thermemoters.size}",
                                fontFamily = hostGrotesk,
                                fontWeight = FontWeight.Medium,
                                color = ThermemoterColors.textSecondary,
                                fontSize = 17.sp
                            )

                        }

                        Spacer(Modifier.height(20.dp))

                        LazyHorizontalGrid(
                            modifier = Modifier.height(24.dp * 10 + 4.dp * (10 - 1)),
                            rows = GridCells.Fixed(10),
                            horizontalArrangement = Arrangement.spacedBy(24.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(20) { index ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(
                                        space = 4.dp, Alignment.CenterHorizontally
                                    ),
                                    modifier = Modifier
                                        .height(24.dp)
                                        .animateContentSize()
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_thermometer),
                                        contentDescription = "thermometer",
                                        tint = if (!state.thermemoters[index].filled) {
                                            ThermemoterColors.textDisabled
                                        } else ThermemoterColors.primary,
                                        modifier = Modifier.size(16.dp)
                                    )

                                    if (state.thermemoters[index].filled) {
                                        Text(
                                            text = "${state.thermemoters[index].value} °F",
                                            fontFamily = hostGrotesk,
                                            fontWeight = FontWeight.Medium,
                                            color = ThermemoterColors.textPrimary,
                                            fontSize = 17.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    ThermemoterState.Idle -> {
                        Text(
                            text = "Press Start to begin tracking temperature",
                            fontFamily = hostGrotesk,
                            fontWeight = FontWeight.Medium,
                            color = ThermemoterColors.textPrimary,
                            fontSize = 17.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(Modifier.height(32.dp))

                ThermemoterButton(
                    text = if (state.thermemoterState == ThermemoterState.Idle) {
                        "Start"
                    } else {
                        if (state.thermemoters.count { it.filled } != state.thermemoters.size) "Tracking..." else "Reset"
                    },
                    enabled = state.thermemoters.count { it.filled } == state.thermemoters.size,
                    onClick = {
                        if (state.thermemoterState == ThermemoterState.Idle) {
                            onAction(ThermemoterTrekActions.OnStartClick)
                        } else {
                            onAction(ThermemoterTrekActions.OnResetClick)
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

        }
    }
}

@Preview
@Composable
private fun ThermemoterTrekScreenPreview() {
    ThermemoterTrekScreen(
        state = ThermemoterTrekUiState(),
        onAction = { }
    )
}