package com.example.socialapp.feature_posts.presentation.pictures

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.use_case.LogoutUseCase
import com.example.socialapp.feature_posts.domain.use_case.DeletePictures.DeletePicturesUseCase
import com.example.socialapp.feature_posts.domain.use_case.SeePictures.SeePicturesUseCase
import com.example.socialapp.feature_posts.domain.use_case.getPictures.GetPicturesUseCase
import com.example.socialapp.feature_posts.domain.use_case.likePictures.LikePicturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureListViewModel @Inject constructor(
    private val getPicturesUseCase: GetPicturesUseCase,
    private val deletePicturesUseCase: DeletePicturesUseCase,
    private val likePicturesUseCase: LikePicturesUseCase,
    private val seePicturesUseCase: SeePicturesUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = mutableStateOf(PictureListState())
    val state: State<PictureListState> = _state
    private var searchJob: Job? = null

    init {
        getPictures(
            query = "", fetchFromRemote = true
        )
    }

    fun onEvent(event: PictureListEvent) {
        when (event) {

            is PictureListEvent.Refresh -> {
                viewModelScope.launch {
                    getPictures(query = "", fetchFromRemote = true)
                }
            }

            is PictureListEvent.OnSearchQueryChange -> {
                _state.value = state.value.copy(
                    searchQuery = event.query
                )
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getPictures(
                        query = state.value.searchQuery, fetchFromRemote = false
                    )
                }
            }

            is PictureListEvent.DeletePicture -> {
                viewModelScope.launch {
                    val pictures = _state.value.pictures.filter {
                        it.id != event.picture.id
                    }
                    _state.value = state.value.copy(
                        pictures = pictures
                    )
                    deletePicturesUseCase.invoke(event.picture)
                }
            }

            is PictureListEvent.LikePicture -> {
                viewModelScope.launch {
                    val pictures = _state.value.pictures.map { picture ->
                        if (picture.id == event.id) {
                            likePicturesUseCase.invoke(picture.copy(isLiked = !picture.isLiked))
                            picture.copy(isLiked = !picture.isLiked)
                        } else {
                            picture
                        }
                    }
                    _state.value = state.value.copy(
                        pictures = pictures
                    )
                }
            }

            is PictureListEvent.SeenPicture -> {
                viewModelScope.launch {
                    val pictures = _state.value.pictures.map { picture ->
                        if (picture.id == event.id && !picture.seen) {
                            seePicturesUseCase.invoke(picture.copy(seen = true))
                            picture.copy(seen = true)
                        } else {
                            picture
                        }
                    }
                    _state.value = state.value.copy(
                        pictures = pictures
                    )
                }
            }

            is PictureListEvent.LogOut -> {
                viewModelScope.launch {
                    logoutUseCase.invoke()
                }
            }
        }
    }

    private fun getPictures(query: String, fetchFromRemote: Boolean) {
        viewModelScope.launch {
            getPicturesUseCase.invoke(query, fetchFromRemote).collect { result ->
                when (result) {
                    is Resource.Success -> {

                        Log.e("dataPictures", "${result.data}")

                        _state.value = PictureListState(
                            pictures = result.data ?: emptyList(),
                        )

                    }
                    is Resource.Error -> _state.value = PictureListState(
                        error = result.message ?: "An unexpected error occur ",
                    )

                    is Resource.Loading -> {

                        Log.e("dataPictures", "${result.data}")

                        _state.value = PictureListState(
                            isLoading = true,
                        )
                    }
                }
            }
        }
    }
}