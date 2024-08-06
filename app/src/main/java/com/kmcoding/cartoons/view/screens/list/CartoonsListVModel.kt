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
class CartoonsListVModel @Inject constructor() : ViewModel() {

}