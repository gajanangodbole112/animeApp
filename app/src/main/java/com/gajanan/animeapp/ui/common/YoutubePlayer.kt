package com.gajanan.animeapp.ui.common

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun YouTubePlayer(url: String, videoId: String) {
    val context = LocalContext.current
    val webViewAvailable = remember {
        try {
            WebView(context)
            true
        } catch (e: Exception) {
            Log.e("WebViewError", "WebView is not available", e)
            false
        }
    }

    if (!webViewAvailable) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                `package` = "com.google.android.youtube"
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(browserIntent)
            } catch (browserException: Exception) {
                Toast.makeText(context, "Unable to open YouTube or browser", Toast.LENGTH_SHORT).show()
            }
        }
        return
    }

    if (videoId.isNotEmpty()){
        AndroidView(
            factory = {
                WebView(it).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.mediaPlaybackRequiresUserGesture = false
                    webViewClient = WebViewClient()
                    loadDataWithBaseURL(null, getYouTubeHtml(videoId), "text/html", "utf-8", null)
                }
            },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Black),
        )
    }

}

fun getYouTubeHtml(videoId: String): String {
    return """
        <!DOCTYPE html>
        <html>
         <head>
            <style>
                body {
                    margin: 0; /* Removes default margin */
                    padding: 0; /* Removes default padding */
                    background-color: black; /* Change background color */
                    display: flex;
                    justify-content: center;
                    align-items: center;
           
                }
               
            </style>
        </head>
        <body>
            <div id="player"></div>
            <script>
                var tag = document.createElement('script');
                tag.src = "https://www.youtube.com/iframe_api";
                var firstScriptTag = document.getElementsByTagName('script')[0];
                firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

                var player;
                function onYouTubeIframeAPIReady() {
                    player = new YT.Player('player', {
                         height: '200',
                        width: '100%',
                        videoId: '$videoId',
                        playerVars: { 'playsinline': 1 },
                        events: {
                            'onReady': onPlayerReady,
                            'onStateChange': onPlayerStateChange
                        }
                    });
                }

                function onPlayerReady(event) {
                    event.target.playVideo();
                }

                function onPlayerStateChange(event) {
                    if (event.data == YT.PlayerState.PLAYING) {
                        console.log("Video is playing");
                    }
                }
            </script>
        </body>
        </html>
    """.trimIndent()
}

