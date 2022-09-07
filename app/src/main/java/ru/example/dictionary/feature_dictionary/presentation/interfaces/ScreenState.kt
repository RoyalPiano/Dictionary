package ru.example.dictionary.feature_dictionary.presentation.interfaces

import ru.example.dictionary.core.util.UiText

interface ScreenState<T> {
    val isLoading: Boolean
    val errorMessage: UiText?
    val data: T
}