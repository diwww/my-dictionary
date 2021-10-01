package org.maxsur.mydictionary.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single
import org.maxsur.mydictionary.data.model.local.WordEntity

@Dao
interface DictionaryDao {

    @Query("SELECT * FROM word ORDER BY uid DESC")
    fun getAllWords(): Single<List<WordEntity>>

    @Query("SELECT * FROM word WHERE original LIKE '%' || :search || '%' OR translated LIKE '%' || :search || '%' ORDER BY uid DESC")
    fun searchWords(search: String): Single<List<WordEntity>>

    @Insert
    fun saveWord(word: WordEntity)
}