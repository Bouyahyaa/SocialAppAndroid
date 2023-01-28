package com.example.cleanarchitectureunsplashapp.presentation.stories.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.socialapp.feature_posts.domain.model.Story
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun StoryImage(
    modifier: Modifier,
    pagerState: PagerState,
    listOfStories: List<Story>,
    setImageLoaded: (String) -> Unit,
) {
    HorizontalPager(
        state = pagerState,
        dragEnabled = false,
        modifier = modifier
    ) { page ->

        val painter = rememberImagePainter(data = listOfStories[page].picture)
        val painterState = painter.state

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        if (painterState is ImagePainter.State.Success) {
            setImageLoaded(listOfStories[currentPage].id)
        } else {
            CircularProgressIndicator(
                color = Color.LightGray,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}