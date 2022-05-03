    package uz.evkalipt.sevenmodullesson121

import android.app.Activity.RESULT_OK
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import uz.evkalipt.sevenmodullesson121.adapters.RvAdapter
import uz.evkalipt.sevenmodullesson121.databinding.FragmentFirstBinding
import uz.evkalipt.sevenmodullesson121.db.MyDb
import uz.evkalipt.sevenmodullesson121.entities.Word3
import uz.evkalipt.sevenmodullesson121.models.MeaningOne
import uz.evkalipt.sevenmodullesson121.models.WordShared
import uz.evkalipt.sevenmodullesson121.viewModels.MyViewModel
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@Suppress("DEPRECATION")
class FirstFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentFirstBinding
    lateinit var myDb: MyDb
    lateinit var rvAdapter: RvAdapter
    lateinit var myViewModel: MyViewModel
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var gson: Gson
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(layoutInflater)
        myDb = MyDb.getInstance(binding.root.context)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        sharedPreferences = binding.root.context.getSharedPreferences("pdp", 0)
        editor = sharedPreferences.edit()
        gson = Gson()

        var format = SimpleDateFormat("dd.MM.yyyy")
        var date = format.format(Date())
        binding.dateTv.text = date.toString()

        myViewModel.getWords2(binding.root.context).observe(this, {
            loadRv(it)
            loadRvBottom(it)

        })

        binding.editFirst.setOnEditorActionListener { p0, p1, p2 ->
            if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                if (!binding.editFirst.text.toString().trim().equals("")) {

                    myViewModel.getWords(binding.editFirst.text.toString()).observe(this, {
                        var word2 = it[0]
                        binding.wordTv.text = word2.word
                        binding.linear2.setOnClickListener {
                            MediaPlayer().apply {
                                setAudioAttributes(
                                    AudioAttributes.Builder()
                                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                        .setUsage(AudioAttributes.USAGE_MEDIA)
                                        .build()
                                )
                                setDataSource(word2.phonetics[0].audio)
                                prepare()
                                start()
                            }
                        }
                        var word3 = Word3(word2.word, word2.origin, word2.phonetic, 0)
                        var listMeaning = ArrayList<MeaningOne>()
                        for (meaning in word2.meanings) {
                            if (meaning.definitions[0].example==null){
                                var meaningOne = MeaningOne(meaning.definitions[0].definition, meaning.partOfSpeech, "null")
                                listMeaning.add(meaningOne)
                            }else{
                                var meaningOne = MeaningOne(meaning.definitions[0].definition, meaning.partOfSpeech, meaning.definitions[0].example)
                                listMeaning.add(meaningOne)
                            }

                        }
                        editor.putString("word", word2.word)
                        editor.commit()
                        val toJson = gson.toJson(listMeaning)
                        editor.putString("wordList", toJson)
                        editor.commit()
                        var boolean = false
                        for (allWord in myDb.wordDao().getAllWords()) {
                            if (word2.word == allWord.word) {
                                boolean = true
                                break
                            }
                        }
                        if (!boolean) {
                            myDb.wordDao().addWord(word3)
                        }
                        loadRv(myDb.wordDao().getAllWords())

                        binding.linear3.setOnClickListener {

                            for (allWord in myDb.wordDao().getAllWords()) {
                                if (allWord.word == binding.editFirst.text.toString()){
                                    if (allWord.save == 0){
                                        var word35 = Word3(allWord.word, allWord.origin, allWord.phonetic, 1)
                                        word35.id = allWord.id
                                        myDb.wordDao().editWord(word35)
                                        binding.imv2.visibility = View.VISIBLE
                                    }else if (allWord.save == 1){
                                        var word35 = Word3(allWord.word, allWord.origin, allWord.phonetic, 0)
                                        word35.id = allWord.id
                                        myDb.wordDao().editWord(word35)
                                        binding.imv2.visibility = View.INVISIBLE
                                    }
                                    break
                                }
                            }
                            loadRv(myDb.wordDao().getAllWords())
                            loadRvBottom(myDb.wordDao().getAllWords())
                        }

                        binding.copy.setOnClickListener {
                            val systemService = ContextCompat.getSystemService(
                                binding.root.context,
                                ClipboardManager::class.java
                            )
                            systemService?.setPrimaryClip(ClipData.newPlainText("", binding.editFirst.text.toString()))
                            Toast.makeText(binding.root.context, "Copied!", Toast.LENGTH_SHORT)
                                .show()
                        }

                        binding.linear1.setOnClickListener {
                            val systemService = ContextCompat.getSystemService(
                                binding.root.context,
                                ClipboardManager::class.java
                            )
                            systemService?.setPrimaryClip(ClipData.newPlainText("", binding.editFirst.text.toString()))
                            Toast.makeText(binding.root.context, "Copied!", Toast.LENGTH_SHORT)
                                .show()
                        }

                        binding.linear4.setOnClickListener {
                            var intent = Intent()
                            intent.action = Intent.ACTION_SEND
                            intent.putExtra(Intent.EXTRA_TEXT, binding.editFirst.text.toString())
                            intent.type = "text/plain"
                            startActivity(intent)
                        }

                    })

                } else {
                    Toast.makeText(binding.root.context, "So'z kirgizing!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            true
        }

        binding.imageView2.setOnClickListener {
            var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

            try {
                startActivityForResult(intent, 1)
            }catch (e:Exception){
                Toast.makeText(binding.root.context, "${e.message}", Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1){
            if (resultCode == RESULT_OK && data!=null){
               var result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!!
                binding.editFirst.setText(Objects.requireNonNull(result).get(0))
            }
        }
    }

    fun loadRv(list: List<Word3>) {
        rvAdapter = RvAdapter(list, object : RvAdapter.MyOnClick {
            override fun clickSave(word3: Word3, position: Int) {
                var word32 = Word3(word3.word, word3.origin, word3.phonetic, 1)
                word32.id = word3.id
                myDb.wordDao().editWord(word32)
                loadRv(myDb.wordDao().getAllWords())
                loadRvBottom(myDb.wordDao().getAllWords())
            }

            override fun clickSave2(word3: Word3, position: Int) {
                var word32 = Word3(word3.word, word3.origin, word3.phonetic, 0)
                word32.id = word3.id
                myDb.wordDao().editWord(word32)
                loadRv(myDb.wordDao().getAllWords())
                loadRvBottom(myDb.wordDao().getAllWords())
            }

        })
        binding.rvTop.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTop.adapter = rvAdapter
    }

    fun loadRvBottom(list: List<Word3>) {
        var list2 = ArrayList<Word3>()
        for (i in list.indices) {
            if (list[i].save == 1) {
                list2.add(list[i])
            }
        }
        rvAdapter = RvAdapter(list2, object : RvAdapter.MyOnClick {
            override fun clickSave(word3: Word3, position: Int) {
                var word32 = Word3(word3.word, word3.origin, word3.phonetic, 1)
                word32.id = word3.id
                myDb.wordDao().editWord(word32)
                loadRv(myDb.wordDao().getAllWords())
                loadRvBottom(myDb.wordDao().getAllWords())
            }

            override fun clickSave2(word3: Word3, position: Int) {
                var word32 = Word3(word3.word, word3.origin, word3.phonetic, 0)
                word32.id = word3.id
                myDb.wordDao().editWord(word32)
                loadRv(myDb.wordDao().getAllWords())
                loadRvBottom(myDb.wordDao().getAllWords())
            }

        })
        binding.rvBottom.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvBottom.adapter = rvAdapter
    }

//    suspend fun getWord(word:String):List<Word2>{
//        return GlobalScope.async(Dispatchers.IO) {
//            ApiClient.apiService.getWord(word)
//        }.await()
//    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}