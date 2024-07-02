package com.kmcoding.cartoons.view.screens

import androidx.lifecycle.ViewModel
import com.kmcoding.cartoons.domain.model.Cartoon
import com.kmcoding.cartoons.domain.repository.CartoonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CartoonsListViewModel @Inject constructor(private val cartoonRepository: CartoonRepository) :
  ViewModel() {

  private val _cartoons = MutableStateFlow<List<Cartoon>>(listOf())
  val cartoons = _cartoons.asStateFlow()

  init {

  }

}