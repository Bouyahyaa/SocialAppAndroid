package com.example.cleanarchitectureunsplashapp.presentation.stories.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserImageNameStoryItem(
    modifier: Modifier,
    painter: Painter,
    username: String,
    contentDescription: String,
) {
    Row(
        modifier
            .fillMaxSize()
            .padding(start = 15.dp, top = 5.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            modifier = modifier
                .clip(CircleShape)
                .size(30.dp),
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = modifier.size(10.dp))

        Box(modifier = modifier.padding(5.dp)) {
            Text(
                text = username,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}