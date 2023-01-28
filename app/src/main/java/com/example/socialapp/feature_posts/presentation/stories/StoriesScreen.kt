package com.example.socialapp.feature_posts.presentation.stories

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.cleanarchitectureunsplashapp.presentation.stories.components.LinearIndicator
import com.example.cleanarchitectureunsplashapp.presentation.stories.components.StoryImage
import com.example.cleanarchitectureunsplashapp.presentation.stories.components.UserImageNameStoryItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun StoriesScreen(
    userPictureUrl: String?,
    username: String,
    pictureId: String,
    navController: NavController,
    viewModel: StoriesViewModel = hiltViewModel(),
) {

    val userPicture = userPictureUrl?.replace(
        "%3A", ":"
    )?.replace("%2F", "/")

    val state = viewModel.state.value

    val pagerState = rememberPagerState(pageCount = state.stories.size)

    val coroutineScope = rememberCoroutineScope()

    var currentPage by remember {
        mutableStateOf(0)
    }

    var pauseTimer by remember {
        mutableStateOf(false)
    }

    val alpha: Float by animateFloatAsState(if (!pauseTimer) 1f else 0.00f)

    var seen: Boolean by remember {
        mutableStateOf(false)
    }
    var offsetY by remember { mutableStateOf(0f) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)
        .offset {
            IntOffset(0, offsetY.roundToInt())
        }
        .draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                if (delta > 0f && offsetY < 350f) {
                    offsetY += delta
                }
            },
            startDragImmediately = false,
            onDragStarted = {
                pauseTimer = true
            },
            onDragStopped = {
                pauseTimer = false
                if (offsetY >= 350f) {
                    navController.previousBackStackEntry?.savedStateHandle?.set("pictureId",
                        pictureId)
                    navController.previousBackStackEntry?.savedStateHandle?.set("seen",
                        seen)
                    navController.popBackStack()
                } else {
                    offsetY = 0f
                }
            }
        )) {

        if (!seen) {
            seen = state.stories.size - 1 == currentPage
        }

        StoryImage(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
                .pointerInput(Unit) {
                    val maxWidth = this.size.width // (2)
                    detectTapGestures(
                        onPress = {
                            val pressStartTime = System.currentTimeMillis()
                            awaitRelease() // (4)
                            pauseTimer = false
                            val pressEndTime = System.currentTimeMillis()
                            val totalPressTime = pressEndTime - pressStartTime // (5)
                            if (totalPressTime < 200) {
                                val isTapOnRightTwoTiers = (it.x > (maxWidth / 4)) // (6)
                                if (isTapOnRightTwoTiers) {
                                    Log.e("TapFinger", "right")
                                    if (currentPage < state.stories.size - 1) {
                                        viewModel.onEvent(StoriesEvent.RightTap(currentPage))
                                        currentPage++
                                        pagerState.animateScrollToPage(currentPage)
                                    }
                                } else {
                                    Log.e("TapFinger", "left")
                                    if (currentPage > 0) {
                                        viewModel.onEvent(StoriesEvent.LeftTap(currentPage))
                                        currentPage--
                                        pagerState.animateScrollToPage(currentPage)
                                    }
                                }
                            }
                        },
                        onLongPress = {
                            pauseTimer = true
                        }
                    )
                },
            pagerState = pagerState,
            listOfStories = state.stories,
            setImageLoaded = { id ->
                viewModel.onEvent(StoriesEvent.ImageLoaded(id = id))
            },
        )

        Column {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {

                for (index in state.stories.indices) {
                    val story = state.stories[index]
                    LinearIndicator(
                        modifier = Modifier
                            .weight(1f)
                            .alpha(alpha = alpha),
                        progressValue = story.progress,
                        startProgress = story.isLoaded,
                        onPauseTimer = pauseTimer
                    ) {
                        coroutineScope.launch {
                            if (currentPage < state.stories.size - 1) {
                                viewModel.onEvent(StoriesEvent.RightTap(currentPage))
                                currentPage++
                                pagerState.animateScrollToPage(currentPage)
                            } else {
                                navController.previousBackStackEntry?.savedStateHandle?.set("pictureId",
                                    pictureId)
                                navController.previousBackStackEntry?.savedStateHandle?.set("seen",
                                    seen)
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }

            UserImageNameStoryItem(
                modifier = Modifier.alpha(alpha = alpha),
                painter = rememberImagePainter(data = userPicture),
                username = username,
                contentDescription = "description"
            )
        }

        BackHandler {
            navController.previousBackStackEntry?.savedStateHandle?.set("pictureId",
                pictureId)
            navController.previousBackStackEntry?.savedStateHandle?.set("seen",
                seen)
            navController.popBackStack()
        }
    }
}