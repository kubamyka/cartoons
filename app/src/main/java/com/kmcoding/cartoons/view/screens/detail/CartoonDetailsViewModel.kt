package com.kmcoding.cartoons.view.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.kmcoding.cartoons.domain.model.Cartoon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CartoonDetailsViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
  ViewModel() {

  private val _cartoon = MutableStateFlow(savedStateHandle.toRoute<Cartoon>())
  val cartoon = _cartoon.asStateFlow()
}