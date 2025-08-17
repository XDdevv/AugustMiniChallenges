package zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation.model

data class ParcelImage(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val size: String? = null,
    val measuredTime: String? = null,
    val fetching: Boolean,
)
