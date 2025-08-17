package zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation.model.ParcelImage
import zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation.model.ParcelState
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.roundToInt
import kotlin.random.Random

class ParcelPigeonRaceViewModel : ViewModel() {

    private val _state = MutableStateFlow(ParcelPigeonRaceState())
    val state = _state.asStateFlow()

    init {
        trackImages()
    }

    private fun trackImages() {
        viewModelScope.launch {
            _state.collect { state ->
                val images = state.images
                if (images.isNotEmpty()) {
                    if (images.all { !it.fetching }) {
                        val time = _state.value.images.maxOf {
                            it.measuredTime?.removeSuffix(" s")?.toDouble() ?: 0.0
                        }

                        _state.update {
                            it.copy(
                                totalTime = "$time s",
                                parcelState = ParcelState.Finished
                            )
                        }
                    }
                }
            }
        }
    }

    fun onAction(action: ParcelPigeonRaceAction) {
        when (action) {
            ParcelPigeonRaceAction.OnRunAgainClick -> {
                restoreList()
            }

            ParcelPigeonRaceAction.OnRunLoadingClick -> {
                restoreList()
            }

            is ParcelPigeonRaceAction.OnImageLoaded -> {
                val list = _state.value.images.toMutableList()
                val targetIndex = list.indexOfFirst { it.id == action.image.id }

                if (targetIndex != -1) {
                    val image = list[targetIndex].copy(
                        measuredTime = millisToSec(action.measuredTime),
                        fetching = false
                    )

                    list[targetIndex] = image

                    _state.update {
                        it.copy(
                            images = list
                        )
                    }
                }
            }

              is ParcelPigeonRaceAction.OnImageSizeLoaded -> {
                val list = _state.value.images.toMutableList()
                val targetIndex = list.indexOfFirst { it.id == action.image.id }

                if (targetIndex != -1) {
                    val image = list[targetIndex].copy(
                        size = bytesToMB(action.size),
                        fetching = false
                    )

                    list[targetIndex] = image

                    _state.update {
                        it.copy(
                            images = list
                        )
                    }
                }
            }
        }
    }

    private fun bytesToMB(bytes: Long): String {
        return "%.2f MB".format(bytes / 1_000_000.0)
    }

    private fun millisToSec(millis: Long): String {
        return "%.1f s".format(millis.toDouble() / 1000.0)
    }

    private fun restoreList() {
        _state.update {
            it.copy(
                images = listOf(),
                parcelState = ParcelState.Running
            )
        }

        val list = Array(6) { id ->
            val imageSize = if(id >= 3) 800 else 1600

            ParcelImage(
                id = System.currentTimeMillis() + id,
                title = "Image#${id + 1}",
                imageUrl = "https://picsum.photos/${imageSize}/${imageSize}?random=${System.currentTimeMillis()}",
                size = null,
                measuredTime = null,
                fetching = true
            )
        }.toList()

        _state.update {
            it.copy(
                images = list,
                totalTime = null
            )
        }

    }

}