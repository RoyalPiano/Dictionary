package ru.example.dictionary.feature_dictionary.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.example.dictionary.core.util.Resource
import ru.example.dictionary.feature_dictionary.domain.model.WordInfo

interface WordInfoRepository {

    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}