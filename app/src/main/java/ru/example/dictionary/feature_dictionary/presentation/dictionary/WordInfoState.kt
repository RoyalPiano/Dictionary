package ru.example.dictionary.feature_dictionary.presentation.dictionary

import ru.example.dictionary.core.util.UiText
import ru.example.dictionary.feature_dictionary.domain.model.WordInfo
import ru.example.dictionary.feature_dictionary.presentation.interfaces.ScreenState

data class WordInfoState(
    override val isLoading: Boolean = false,
    override val data: List<WordInfo> = emptyList(),
    override val errorMessage: UiText? = null
): ScreenState<List<WordInfo>>