package com.kmcoding.cartoons.view.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmcoding.cartoons.domain.model.Cartoon
import com.kmcoding.cartoons.domain.repository.CartoonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartoonsListViewModel @Inject constructor(private val cartoonRepository: CartoonRepository) :
  ViewModel() {

  private val _cartoons = MutableStateFlow<List<Cartoon>>(listOf())
  val cartoons = _cartoons.asStateFlow()

  init {
    fetchCartoons()
  }

  private fun fetchCartoons() {
    viewModelScope.launch {
      cartoonRepository.getCartoons()
        .flowOn(Dispatchers.IO)
        .catch { error ->

        }.collect { cartoons ->
          _cartoons.update { cartoons }
        }
    }
  }
}