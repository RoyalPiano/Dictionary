package ru.example.dictionary.feature_dictionary.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import ru.example.dictionary.core.util.Constants
import ru.example.dictionary.core.util.Resource
import ru.example.dictionary.feature_dictionary.domain.model.WordInfo
import ru.example.dictionary.feature_dictionary.domain.use_case.GetWordInfoUseCase
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val getWordInfoUseCase: GetWordInfoUseCase
): ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _state = MutableStateFlow<Resource<List<WordInfo>>>(Resource.Loading())
    val state: StateFlow<Resource<List<WordInfo>>> = _state

    private val _wordInfos = MutableStateFlow<List<WordInfo>>(emptyList())
    val wordInfos: StateFlow<List<WordInfo>> = _wordInfos

    private var searchJob: Job? = null

    init {
        onSearch("")
    }

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
                        _wordInfos.emit(result.data)
                    }
                    is Resource.Error -> {}
                    is Resource.Loading -> {
                        result.data?.let { _wordInfos.emit(it) }
                    }
                }

                _state.emit(result)
            }.launchIn(viewModelScope)
        }
    }