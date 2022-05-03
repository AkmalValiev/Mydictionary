package uz.evkalipt.sevenmodullesson121.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import uz.evkalipt.sevenmodullesson121.modelsWord.Word2

interface ApiService {

    @GET("en/{word}")
    suspend fun getWord(@Path("word") word:String):List<Word2>

}