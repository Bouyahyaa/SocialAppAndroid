package com.example.socialapp.feature_posts.presentation.pictures.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun StoryListItem(
    painterStoryImage: Painter,
    contentDescription: String,
    seen: Boolean,
    onItemClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .height(90.dp)
            .width(93.5.dp)
            .padding(12.dp)
            .border(
                width = 2.dp,
                brush = if (seen) Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF707070),
                        Color(0xFF888888)
                    ),
                ) else Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFF71458),
                        Color(0xFFFA95AC)
                    ),
                ),
                shape = RoundedCornerShape(100)
            )
            .clickable {
                onItemClick()
            }

    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(CircleShape),
            painter = painterStoryImage,
            contentDescription = contentDescription,
        )
    }
}