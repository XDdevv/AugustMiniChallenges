package zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation

import zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation.model.ParcelImage

sealed interface ParcelPigeonRaceAction {
    data object OnRunLoadingClick : ParcelPigeonRaceAction

    data object OnRunAgainClick : ParcelPigeonRaceAction

    data class OnImageLoaded(
        val image: ParcelImage,
        val measuredTime: Long,
    ) : ParcelPigeonRaceAction

    data class OnImageSizeLoaded(
        val image: ParcelImage,
        val size: Long,
    ) : ParcelPigeonRaceAction
}