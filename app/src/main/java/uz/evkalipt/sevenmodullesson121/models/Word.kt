package uz.evkalipt.sevenmodullesson121.models

import java.io.Serializable

class Word : Serializable {

    var title:String? = null
    var description:String? = null

    constructor(title: String?, description: String?) {
        this.title = title
        this.description = description
    }

    constructor()

}