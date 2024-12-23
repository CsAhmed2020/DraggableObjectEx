package com.example.draggableobject.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun AnimatedItem(penguin: Int,isPlaying: Boolean) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(penguin)
    )

    val progress by animateLottieCompositionAsState(composition = composition,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever,
        speed = 1f,
        restartOnPlay = false
    )

    LottieAnimation(composition = composition,
        progress = progress,
        modifier = Modifier
            .size(180.dp)
            .padding(top = 5.dp)
            .wrapContentHeight()

    )
}
