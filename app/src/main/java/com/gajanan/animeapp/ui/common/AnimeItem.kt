package com.gajanan.animeapp.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.gajanan.animeapp.ui.theme.DarkBlue
import com.gajanan.animeapp.ui.theme.DimYellow
import com.gajanan.animeapp.R
import com.gajanan.animeapp.model.AnimeResponse

@Composable
fun AnimeItem(data: AnimeResponse, onClick:(id:Int) -> Unit) {
    val context = LocalContext.current
    val imageRequest = remember(data.images.jpg.large_image_url) {
        ImageRequest.Builder(context)
            .data(data.images.jpg.large_image_url)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .border(
                border = BorderStroke(width = .5.dp, color = Color.LightGray.copy(alpha = .5f)),
                shape = RoundedCornerShape(5.dp)
            )
            .background(Color.LightGray.copy(.2f), shape = RoundedCornerShape(5.dp))
            .clickable {
                onClick(data.mal_id)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .requiredHeightIn(min = 250.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.LightGray)
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black)
                        )
                    )
                    .padding(horizontal = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterStart)
                        .background(DarkBlue, shape = RoundedCornerShape(5.dp))
                        .padding(horizontal = 5.dp)
                ) {
                    Text(
                        text = "Ep ${data.episodes}",
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    Modifier
                        .align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_rating),
                        contentDescription = "",
                        tint = DimYellow
                    )
                    Text(
                        text = "${data.score}",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(10.dp),
            text = data.title,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            fontSize = 14.sp
        )
    }
}