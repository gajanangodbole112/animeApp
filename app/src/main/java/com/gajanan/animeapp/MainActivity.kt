package com.gajanan.animeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import com.gajanan.animeapp.ui.navigation.AppNavigator
import com.gajanan.animeapp.ui.theme.RerTheme
import com.gajanan.animeapp.ui.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  enableEdgeToEdge()
        setContent {
            RerTheme {
                val viewModel = hiltViewModel<HomeViewModel>()
                viewModel.getAllTopAnime()
                Surface {
                    AppNavigator()
                }
            }
        }
    }
}