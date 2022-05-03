package uz.evkalipt.sevenmodullesson121

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.evkalipt.sevenmodullesson121.adapters.RvAdapterPagerSearch
import uz.evkalipt.sevenmodullesson121.databinding.FragmentPagerSearchBinding
import uz.evkalipt.sevenmodullesson121.models.ForSearchPager
import uz.evkalipt.sevenmodullesson121.models.MeaningOne

private const val ARG_PARAM1 = "param1"

class PagerSearchFragment : Fragment() {
    private var param1: ForSearchPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as ForSearchPager
        }
    }
    lateinit var binding: FragmentPagerSearchBinding
    lateinit var rvAdapterPagerSearch: RvAdapterPagerSearch
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPagerSearchBinding.inflate(layoutInflater)
        rvAdapterPagerSearch = RvAdapterPagerSearch(param1!!.words)
        binding.rvSearchPager.adapter = rvAdapterPagerSearch

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: ForSearchPager) =
            PagerSearchFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}