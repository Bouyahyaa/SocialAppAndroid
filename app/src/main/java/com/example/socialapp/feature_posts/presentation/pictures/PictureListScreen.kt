package com.example.socialapp.feature_posts.presentation.pictures

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.socialapp.feature_posts.presentation.pictures.components.PictureListItem
import com.example.socialapp.feature_posts.presentation.pictures.components.SearchView
import com.example.socialapp.feature_posts.presentation.pictures.components.StoryListItem
import com.example.socialapp.core.util.Screen
import com.example.socialapp.graphs.Graph
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun PictureListScreen(
    authNavController: NavController,
    navController: NavController,
    viewModel: PictureListViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    val text = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )

    val currentBackStackEntry = navController.currentBackStackEntry!!
    val savedStateHandle = currentBackStackEntry.savedStateHandle

    val pictureId = savedStateHandle.get<String>("pictureId")
    val seen = savedStateHandle.get<Boolean>("seen")

    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        viewModel.onEvent(PictureListEvent.Refresh)
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
        ) {
            LazyRow {
                if (seen != null && seen && pictureId != null) {
                    viewModel.onEvent(PictureListEvent.SeenPicture(pictureId))
                    savedStateHandle.remove<String>("pictureId")
                    savedStateHandle.remove<Boolean>("seen")
                }
                item {
                    IconButton(
                        modifier = Modifier
                            .height(90.dp)
                            .width(93.5.dp)
                            .padding(top = 12.dp, bottom = 12.dp),
                        onClick = {
                            viewModel.onEvent(PictureListEvent.LogOut)
                            authNavController.navigate(Screen.LoginScreen.route) {
                                popUpTo(Graph.POSTS) {
                                    inclusive = true
                                }
                            }
                        }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                }
                items(state.pictures) { picture ->
                    StoryListItem(
                        painterStoryImage = rememberImagePainter(picture.large),
                        contentDescription = picture.description!!,
                        seen = picture.seen,
                        onItemClick = {
                            navController.navigate(
                                route = Screen.StoriesScreen.route + "/${
                                    picture.regular?.replace(
                                        ":",
                                        "%3A"
                                    )?.replace("/", "%2F")
                                }" + "/${
                                    picture.large?.replace(
                                        ":",
                                        "%3A"
                                    )?.replace("/", "%2F")
                                }" + "/${picture.username}" + "/${picture.id}"
                            )
                        }
                    )
                }
            }

            SearchView(state = text, onTextChanged = {
                viewModel.onEvent(PictureListEvent.OnSearchQueryChange(text.value.text))
            })

            Spacer(modifier = Modifier.size(7.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    cells = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(state.pictures) { picture ->
                        PictureListItem(
                            painterBaseImage = rememberImagePainter(picture.regular),
                            painterUserImage = rememberImagePainter(picture.small),
                            username = picture.username!!,
                            contentDescription = picture.description!!,
                            liked = picture.isLiked,
                            onDeleteClick = {
                                viewModel.onEvent(
                                    PictureListEvent.DeletePicture(picture, text.value.text)
                                )
                            },
                            onLikeClick = {
                                viewModel.onEvent(
                                    PictureListEvent.LikePicture(picture.id)
                                )
                            })
                    }
                }

                if (state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )
                }

                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}