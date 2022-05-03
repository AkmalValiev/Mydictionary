package uz.evkalipt.sevenmodullesson121.dao

import androidx.room.*
import uz.evkalipt.sevenmodullesson121.entities.Word3

@Dao
interface WordDao {

    @Insert
    fun addWord(word3: Word3)

    @Query("select * from word3")
    fun getAllWords():List<Word3>

    @Delete
    fun deleteWord(word3: Word3)

    @Update
    fun editWord(word3: Word3)
}