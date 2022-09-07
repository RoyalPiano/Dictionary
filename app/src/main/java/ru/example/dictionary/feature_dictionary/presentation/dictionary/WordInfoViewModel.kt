package ru.example.dictionary.feature_dictionary.presentation.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import ru.example.dictionary.core.util.Constants
import ru.example.dictionary.core.util.Resource
import ru.example.dictionary.feature_dictionary.domain.use_case.GetWordInfoUseCase
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val getWordInfoUseCase: GetWordInfoUseCase
): ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _state = MutableStateFlow(WordInfoState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    fun refreshData() {
        onSearch(searchQuery.value)
    }

    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = getWordInfoUseCase(query).onStart { delay(Constants.SEARCH_DELAY) }
            .onEach { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.emit(WordInfoState(data = result.data, isLoading = false, errorMessage = null))
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(errorMessage = result.message, isLoading = false) }
                    }
                    is Resource.Loading -> {
                        _state.emit(WordInfoState(isLoading = true, errorMessage = null, data = result.data.orEmpty()))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }