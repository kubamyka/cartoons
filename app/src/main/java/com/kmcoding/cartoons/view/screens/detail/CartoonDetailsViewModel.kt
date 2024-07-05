package com.kmcoding.cartoons.view.screens.detail

import androidx.lifecycle.ViewModel
import com.kmcoding.cartoons.domain.model.Cartoon
import com.kmcoding.cartoons.domain.repository.CartoonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CartoonDetailsViewModel @Inject constructor(
  private val cartoonRepository: CartoonRepository) : ViewModel() {

  private val _cartoon = MutableStateFlow<Cartoon?>(null)
  val cartoon = _cartoon.asStateFlow()
}