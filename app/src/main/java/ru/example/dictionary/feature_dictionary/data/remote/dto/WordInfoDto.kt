package ru.example.dictionary.feature_dictionary.data.remote.dto

import ru.example.dictionary.feature_dictionary.data.local.entity.WordInfoEntity
import ru.example.dictionary.feature_dictionary.domain.model.WordInfo

data class WordInfoDto(
    val license: License,
    val meanings: List<MeaningDto>,
    val phonetic: String,
    val phonetics: List<PhoneticDto>,
    val sourceUrls: List<String>,
    val word: String
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic,
            word = word
        )
    }

    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            word = word,
            phonetic = phonetic,
            meanings = meanings.map { it.toMeaning() }
        )
    }
}