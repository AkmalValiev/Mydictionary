package uz.evkalipt.sevenmodullesson121.models

import java.io.Serializable

data class ForSearchPager(
    var word:String,
    var words:List<MeaningOne>
) : Serializable