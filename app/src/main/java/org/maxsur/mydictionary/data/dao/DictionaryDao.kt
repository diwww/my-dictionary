package org.maxsur.mydictionary.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.maxsur.mydictionary.data.model.local.WordEntity

@Dao
interface DictionaryDao {

    @Query("SELECT * FROM word WHERE original LIKE '%' || :search || '%' OR translated LIKE '%' || :search || '%' ORDER BY uid DESC")
    fun searchWords(search: String): Single<List<WordEntity>>

    @Query("SELECT * FROM word ORDER BY uid DESC")
    fun getAllWordsObservable(): Observable<List<WordEntity>>

    @Query("SELECT * FROM word WHERE original LIKE '%' || :search || '%' OR translated LIKE '%' || :search || '%' ORDER BY uid DESC")
    fun getSearchObservable(search: String): Observable<List<WordEntity>>

    @Insert
    fun saveWord(word: WordEntity): Completable

    @Update
    fun updateWord(word: WordEntity): Completable

    @Query("SELECT * FROM word WHERE uid = :uid LIMIT 1")
    fun getWord(uid: Int): Single<WordEntity>

    @Query("SELECT * FROM word WHERE favorite = 1")
    fun getFavoriteWords(): Single<List<WordEntity>>
}