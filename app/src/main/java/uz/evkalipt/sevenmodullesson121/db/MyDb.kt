package uz.evkalipt.sevenmodullesson121.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.evkalipt.sevenmodullesson121.dao.WordDao
import uz.evkalipt.sevenmodullesson121.entities.Word3

@Database(entities = [Word3::class], version = 1)
abstract class MyDb:RoomDatabase() {

    abstract fun wordDao():WordDao

    companion object{
        private var instance:MyDb? = null

        fun getInstance(context: Context):MyDb{
            if (instance == null){
                instance = Room.databaseBuilder(context, MyDb::class.java, "pdp")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }

}