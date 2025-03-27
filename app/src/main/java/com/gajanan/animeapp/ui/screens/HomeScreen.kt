package com.gajanan.animeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gajanan.animeapp.ui.common.AnimeItem
import com.gajanan.animeapp.ui.viewModels.HomeViewModel

@Composable
fun HomeScreen(navigateCallback: (animeId: Int) -> Unit) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val animeList = viewModel.animeState.value.animeList
    LaunchedEffect(viewModel) {
        viewModel.getAllTopAnime()
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

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (animeList.isNotEmpty()) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(count = 2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalItemSpacing = 10.dp,
                contentPadding = PaddingValues(10.dp)
            ) {
                items(animeList.size) { index ->
                    AnimeItem(
                        data = animeList[index],
                        onClick = { animeId ->
                            navigateCallback(animeId)
                        }
                    )
                }
            }
        }
    }
}