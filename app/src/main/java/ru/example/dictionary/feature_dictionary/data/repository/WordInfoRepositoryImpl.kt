package ru.example.dictionary.feature_dictionary.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.example.dictionary.R
import ru.example.dictionary.core.util.Resource
import ru.example.dictionary.core.util.UiText
import ru.example.dictionary.feature_dictionary.data.local.WordInfoDao
import ru.example.dictionary.feature_dictionary.data.remote.DictionaryAPI
import ru.example.dictionary.feature_dictionary.domain.model.WordInfo
import ru.example.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import java.io.IOException

class WordInfoRepositoryImpl(
    private val api: DictionaryAPI,
    private val dao: WordInfoDao
): WordInfoRepository {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())
        if(word.isBlank()) {
            emit(Resource.Success(emptyList()))
            return@flow
        }
        
        val wordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Loading(wordInfos))

        try {
            val remoteWordInfos = api.getWordInfo(word)
            dao.deleteWordInfos(remoteWordInfos.map { it.word })
            dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })
            val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
            emit(Resource.Success(newWordInfos))
        } catch (e: HttpException) {
            if(e.localizedMessage.isNullOrEmpty()) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_exception)))
            }
            else {
                emit(Resource.Error(UiText.StringResource(R.string.http_404_exception)))
            }
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.io_exception)))
        }
    }

}