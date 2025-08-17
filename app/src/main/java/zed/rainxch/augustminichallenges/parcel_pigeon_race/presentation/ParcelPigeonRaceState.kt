package zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation

import zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation.model.ParcelImage
import zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation.model.ParcelState

data class ParcelPigeonRaceState(
    val parcelState: ParcelState = ParcelState.Idle,
    val totalTime: String? = null,
    val images: List<ParcelImage> = emptyList(),
)