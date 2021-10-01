package org.maxsur.mydictionary.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import org.maxsur.mydictionary.data.database.DictionaryDatabase
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideDictionaryDatabase(): DictionaryDatabase {
        return Room.databaseBuilder(
            context,
            DictionaryDatabase::class.java, "dictionary-database"
        ).build()
    }
}