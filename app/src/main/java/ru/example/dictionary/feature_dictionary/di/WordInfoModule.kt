package ru.example.dictionary.feature_dictionary.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.example.dictionary.core.util.Constants
import ru.example.dictionary.feature_dictionary.data.local.Converters
import ru.example.dictionary.feature_dictionary.data.local.WordInfoDao
import ru.example.dictionary.feature_dictionary.data.local.WordInfoDatabase
import ru.example.dictionary.feature_dictionary.data.remote.DictionaryAPI
import ru.example.dictionary.feature_dictionary.data.util.GsonParser
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideDao(
        db: WordInfoDatabase,
    ): WordInfoDao {
        return db.dao
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase {
        return Room.databaseBuilder(
            app, WordInfoDatabase::class.java, "word_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryAPI::class.java)
    }
}