package ru.example.dictionary.feature_dictionary.domain.use_case

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.example.dictionary.core.util.Constants
import ru.example.dictionary.core.util.Resource
import ru.example.dictionary.feature_dictionary.domain.model.WordInfo
import ru.example.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import javax.inject.Inject

class GetWordInfoUseCase @Inject constructor(private val repository: WordInfoRepository) {

    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        return repository.getWordInfo(word)
    }
}