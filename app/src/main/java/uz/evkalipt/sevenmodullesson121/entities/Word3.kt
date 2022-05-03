package uz.evkalipt.sevenmodullesson121.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Word3 {
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
    @ColumnInfo(name = "word")
    var word:String? = null
    @ColumnInfo(name = "origin")
    var origin: String? = null
    @ColumnInfo(name = "phonetic")
    var phonetic: String? = null
    @ColumnInfo(name = "save")
    var save:Int? = 0

    constructor()
    constructor(word: String?, origin: String?, phonetic: String?, save: Int?) {
        this.word = word
        this.origin = origin
        this.phonetic = phonetic
        this.save = save
    }


}