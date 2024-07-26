package com.kmcoding.cartoons.view.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmcoding.cartoons.R
import com.kmcoding.cartoons.domain.model.Cartoon
import com.kmcoding.cartoons.domain.model.UiText
import com.kmcoding.cartoons.domain.repository.CartoonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartoonsListViewModel @Inject constructor(private val cartoonRepository: CartoonRepository) :
  ViewModel() {

  private val _isLoading = MutableStateFlow(false)
  val isLoading = _isLoading.asStateFlow()

  private val _isSearchActive = MutableStateFlow(false)
  val isSearchActive = _isSearchActive.asStateFlow()

  private val _searchQuery = MutableStateFlow("")
  val searchQuery = _searchQuery.asStateFlow()

  private val _snackBarChannel = Channel<UiText>()
  val snackBarChannel = _snackBarChannel.receiveAsFlow()

  private val _cartoons = MutableStateFlow<List<Cartoon>>(listOf())
  val cartoons = searchQuery.combine(_cartoons) { text, cartoons ->
    if (text.isBlank()) {
      cartoons
    } else {
      cartoons.filter {
        it.title.contains(text, ignoreCase = true)
      }
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _cartoons.value)

  init {
    fetchCartoons()
  }

  fun fetchCartoons() {
    setLoading(true)
    viewModelScope.launch {
      cartoonRepository.getCartoons()
        .flowOn(Dispatchers.IO)
        .catch { error ->
          setLoading(false)
          sendErrorMessage(error)
        }.map { list ->
          list.sortedBy { it.title }
        }.collect { list ->
          setLoading(false)
          _cartoons.update { list }
        }
    }
  }

  private fun setLoading(loading: Boolean) {
    _isLoading.update { loading }
  }

  fun toggleSearchActive() {
    updateQuery("")
    _isSearchActive.update { !it }
  }

  fun updateQuery(query: String) {
    _searchQuery.update { query }
  }

  private suspend fun sendErrorMessage(error: Throwable) {
    val message = error.localizedMessage
    val uiText = if(message == null) {
      UiText.StringResource(R.string.error_download_cartoons)
    } else {
      UiText.DynamicString(message)
    }
    _snackBarChannel.send(uiText)
  }
}