package uz.evkalipt.sevenmodullesson121

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import uz.evkalipt.sevenmodullesson121.adapters.RvAdapter
import uz.evkalipt.sevenmodullesson121.databinding.FragmentSaveBinding
import uz.evkalipt.sevenmodullesson121.db.MyDb
import uz.evkalipt.sevenmodullesson121.entities.Word3
import uz.evkalipt.sevenmodullesson121.viewModels.MyViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SaveFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    lateinit var binding: FragmentSaveBinding
    lateinit var myViewModel: MyViewModel
    lateinit var rvAdapter:RvAdapter
    lateinit var myDb: MyDb
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaveBinding.inflate(layoutInflater)

        myDb = MyDb.getInstance(binding.root.context)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        loadRv()

        return binding.root
    }

    fun loadRv(){
        myViewModel.getWords2(binding.root.context).observe(this, {
            var word3List = ArrayList<Word3>()
            for (word3 in it) {
                if (word3.save == 1){
                    word3List.add(word3)
                }
            }
            rvAdapter = RvAdapter(word3List, object : RvAdapter.MyOnClick{
                override fun clickSave(word3: Word3, position: Int) {
                    var word32 = Word3(word3.word, word3.origin, word3.phonetic, 1)
                    word32.id = word3.id
                    myDb.wordDao().editWord(word32)
                    loadRv()
                }

                override fun clickSave2(word3: Word3, position: Int) {
                    var word32 = Word3(word3.word, word3.origin, word3.phonetic, 0)
                    word32.id = word3.id
                    myDb.wordDao().editWord(word32)
                    loadRv()
                }

            })
            binding.rvSave.adapter = rvAdapter

        })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SaveFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}