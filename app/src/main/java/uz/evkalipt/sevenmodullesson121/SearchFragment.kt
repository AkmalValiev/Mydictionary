package uz.evkalipt.sevenmodullesson121

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.item_for_tab.view.*
import uz.evkalipt.sevenmodullesson121.adapters.MyPagerFragmentAdapter
import uz.evkalipt.sevenmodullesson121.adapters.RvAdapter
import uz.evkalipt.sevenmodullesson121.databinding.FragmentSearchBinding
import uz.evkalipt.sevenmodullesson121.databinding.ItemForTabBinding
import uz.evkalipt.sevenmodullesson121.db.MyDb
import uz.evkalipt.sevenmodullesson121.entities.Word3
import uz.evkalipt.sevenmodullesson121.models.ForSearchPager
import uz.evkalipt.sevenmodullesson121.models.MeaningOne
import uz.evkalipt.sevenmodullesson121.viewModels.MyViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    lateinit var binding: FragmentSearchBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var gson: Gson
    lateinit var list:ArrayList<ForSearchPager>
    lateinit var myPagerFragmentAdapter: MyPagerFragmentAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        gson = Gson()
        sharedPreferences = binding.root.context.getSharedPreferences("pdp", 0)
        val wordString = sharedPreferences.getString("word", "")
        val wordStringList = sharedPreferences.getString("wordList", "")
        if (wordString!="" && wordStringList!=""){
            var type = object :TypeToken<List<MeaningOne>>(){}.type
            val fromJson = gson.fromJson<List<MeaningOne>>(wordStringList, type)

            binding.wordSearch.text = wordString
            list = ArrayList()
            var forSearchPager1 = ForSearchPager("Anlam", fromJson)
            var forSearchPager2 = ForSearchPager("Deyim", fromJson)
            var forSearchPager3 = ForSearchPager("Birlesik Kelime", fromJson)
            list.add(forSearchPager1)
            list.add(forSearchPager2)
            list.add(forSearchPager3)
            myPagerFragmentAdapter = MyPagerFragmentAdapter(list, childFragmentManager)
            binding.viewPager.adapter = myPagerFragmentAdapter
            binding.tabLayout.setupWithViewPager(binding.viewPager)

            val tabCount = binding.tabLayout.tabCount
            for (i in 0 until tabCount){
                var tabView = ItemForTabBinding.inflate(layoutInflater)
                binding.tabLayout.getTabAt(i)?.customView = tabView.root
                tabView.tvSearch1.text = list[i].word
                tabView.tvSearch2.text = list[i].word

                if (i==0){
                    tabView.tvSearch2.visibility = View.VISIBLE
                }else{
                    tabView.tvSearch2.visibility = View.INVISIBLE
                }
            }

            binding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    var tabView = tab?.customView
                    tabView?.tv_search2?.visibility = View.VISIBLE
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    var tabView = tab?.customView
                    tabView?.tv_search2?.visibility = View.INVISIBLE
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })
        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}