package uz.evkalipt.sevenmodullesson121.repository

import uz.evkalipt.sevenmodullesson121.retrofit.ApiService

class WordRepository(var word:String, var apiService: ApiService){

    suspend fun getWords() = apiService.getWord(word)

}