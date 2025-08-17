package zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import okhttp3.OkHttpClient
import zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation.model.ParcelImage
import zed.rainxch.augustminichallenges.ui.theme.ParcelPigeonRaceColors
import zed.rainxch.augustminichallenges.ui.theme.hostGrotesk
import java.util.concurrent.TimeUnit

@Composable
fun ParcelItem(
    parcelImage: ParcelImage,
    onImageLoaded: (measuredTime: Long) -> Unit,
    onImageSizeLoaded: (size: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val startTime = remember(parcelImage.id) { System.currentTimeMillis() }

    val imageLoader = remember(parcelImage.id) {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)

                val url = request.url.toString()
                val contentLength = response.header("Content-Length")?.toLongOrNull()

                if (contentLength != null && contentLength > 0 &&
                    url.contains("fastly.picsum.photos") && url.contains(".jpg")) {
                    onImageSizeLoaded(contentLength)
                }

                response
            }
            .build()

        ImageLoader.Builder(context)
            .okHttpClient(okHttpClient)
            .build()
    }

    val imageRequest = ImageRequest.Builder(context)
        .data(parcelImage.imageUrl)
        .build()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .shadow(
                24.dp,
                RoundedCornerShape(16.dp),
                ambientColor = ParcelPigeonRaceColors.loadingImg,
                spotColor = ParcelPigeonRaceColors.loadingImg
            )
            .background(ParcelPigeonRaceColors.surfaceHigher)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {

            SubcomposeAsyncImage(
                model = imageRequest,
                contentDescription = parcelImage.title,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = ParcelPigeonRaceColors.outlineImg,
                        shape = RoundedCornerShape(8.dp)
                    ),
                loading = {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(ParcelPigeonRaceColors.loadingImg)
                    )
                },
                imageLoader = imageLoader,
                onSuccess = { success ->
                    val duration = System.currentTimeMillis() - startTime

                    onImageLoaded(duration)
                },
                onError = {
                    Log.e("ParcelItem", "${it.result.throwable.localizedMessage}")
                }
            )

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = parcelImage.title,
                    color = ParcelPigeonRaceColors.textPrimary,
                    fontFamily = hostGrotesk,
                    fontWeight = FontWeight.Medium,
                    fontSize = 19.sp
                )

                Text(
                    text = if (parcelImage.fetching) "Fetching..." else parcelImage.size.toString(),
                    color = ParcelPigeonRaceColors.textSecondary,
                    fontFamily = hostGrotesk,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            }
        }

        parcelImage.measuredTime?.let { time ->
            Text(
                text = time,
                fontFamily = hostGrotesk,
                fontWeight = FontWeight.Normal,
                color = ParcelPigeonRaceColors.textSecondary,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(end = 16.dp, bottom = 6.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ParcelItemPreview() {
    ParcelItem(
        parcelImage = ParcelImage(
            title = "Image#1",
            imageUrl = "https://picsum.photos/1000/1000",
            size = null,
            measuredTime = "1.2s",
            fetching = true,
            id = 0
        ),
        onImageLoaded = { _ -> },
        {}
    )
}