package ru.example.dictionary.feature_dictionary.presentation.dictionary

import ru.example.dictionary.core.util.UiText
import ru.example.dictionary.feature_dictionary.domain.model.WordInfo

data class WordInfoState(
    val isLoading: Boolean = false,
    val wordInfos: List<WordInfo> = emptyList(),
    val errorMessage: UiText? = null
)