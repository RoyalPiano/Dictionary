package ru.example.dictionary.feature_dictionary.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.example.dictionary.feature_dictionary.data.repository.WordInfoRepositoryImpl
import ru.example.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(wordInfoRepository: WordInfoRepositoryImpl): WordInfoRepository
}