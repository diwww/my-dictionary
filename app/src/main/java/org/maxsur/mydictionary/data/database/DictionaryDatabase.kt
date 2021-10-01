package org.maxsur.mydictionary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.maxsur.mydictionary.data.dao.DictionaryDao
import org.maxsur.mydictionary.data.model.local.WordEntity

@Database(entities = [WordEntity::class], version = 1)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao
}