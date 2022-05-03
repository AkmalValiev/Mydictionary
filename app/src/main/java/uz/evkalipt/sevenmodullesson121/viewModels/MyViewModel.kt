package uz.evkalipt.sevenmodullesson121.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uz.evkalipt.sevenmodullesson121.db.MyDb
import uz.evkalipt.sevenmodullesson121.entities.Word3
import uz.evkalipt.sevenmodullesson121.modelsWord.Word2
import uz.evkalipt.sevenmodullesson121.retrofit.ApiClient

class MyViewModel:ViewModel() {
    private val liveData = MutableLiveData<List<Word2>>()
    private val liveData2 = MutableLiveData<List<Word3>>()
    lateinit var myDb: MyDb
    fun getWords(word:String):LiveData<List<Word2>>{
        var apiService = ApiClient.apiService

        viewModelScope.launch {
            val async = async { apiService.getWord(word) }

            val await = async.await()
            liveData.value = await
        }
        return liveData
    }

    fun getWords2(context: Context):LiveData<List<Word3>>{
        viewModelScope.launch {
            myDb = MyDb.getInstance(context)
            val allWords = myDb.wordDao().getAllWords()
            liveData2.value = allWords
        }
        return liveData2
    }

}