package uz.evkalipt.sevenmodullesson121.modelsWord

data class Word2(
    val meanings: List<Meaning>,
    val origin: String,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val word: String
)