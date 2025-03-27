package com.gajanan.animeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.gajanan.animeapp.ui.common.YouTubePlayer
import com.gajanan.animeapp.ui.theme.DarkBlue
import com.gajanan.animeapp.ui.theme.DimYellow
import com.gajanan.animeapp.ui.viewModels.HomeViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailScreen(animeId: Int) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val data = viewModel.animeState.value.animeData
    var startPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
        viewModel.getAnimeDetails(animeId)
    }
    val context = LocalContext.current
    val imageRequest = remember(data?.images?.jpg?.large_image_url) {
        ImageRequest.Builder(context)
            .data(data?.images?.jpg?.large_image_url)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()
    }
    if (viewModel.animeState.value.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                modifier = Modifier
                    .weight(.4f)
                    .height(250.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.FillHeight
            )
            Column(
                Modifier
                    .weight(.6f)
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = data?.title ?: "N/A",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                FlowRow(
                    Modifier.padding(top = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "Genre: ",
                        color = DarkBlue,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    data?.genres?.let {
                        repeat(it.size) { index ->
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .background(Color.DarkGray, shape = RoundedCornerShape(3.dp))
                                    .padding(vertical = 2.dp, horizontal = 4.dp),
                                text = it[index].name,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    } ?: Text(text = "N/A")
                }

                Row {
                    Text(
                        text = "Rating: ",
                        color = DarkBlue,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${data?.score ?: "N/A"}"
                    )
                }

                Row {
                    Text(
                        text = "Number of Episodes: ",
                        color = DarkBlue,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${data?.episodes ?: "N/A"}"
                    )
                }
            }
        }

        if (data?.trailer?.url != null || data?.trailer?.youtube_id != null) {
            if (startPlaying) {
                YouTubePlayer(url = data.trailer.url, videoId = data.trailer.youtube_id)
            } else {
                Box(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(DarkBlue, shape = RoundedCornerShape(5.dp))
                        .clickable {
                            startPlaying = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = "Trailer: Click to Play",
                        textAlign = TextAlign.Center,
                        color = DimYellow,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        Text(
            text = "Overview/Synopsis: ",
            fontSize = 16.sp,
            color = DarkBlue,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = data?.synopsis ?: "N/A",
            textAlign = TextAlign.Justify,
            fontSize = 14.sp
        )
    }
}