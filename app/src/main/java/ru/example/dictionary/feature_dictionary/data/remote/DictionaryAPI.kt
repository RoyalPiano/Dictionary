package ru.example.dictionary.feature_dictionary.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import ru.example.dictionary.feature_dictionary.data.remote.dto.WordInfoDto

interface DictionaryAPI {

    @GET("/api/v2/entries/en/{word}")
    suspend fun getWordInfo(
        @Path("word") word: String
    ): List<WordInfoDto>
}