package uz.evkalipt.sevenmodullesson121.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.evkalipt.sevenmodullesson121.PagerSearchFragment
import uz.evkalipt.sevenmodullesson121.models.ForSearchPager
import uz.evkalipt.sevenmodullesson121.models.MeaningOne

@Suppress("DEPRECATION")
class MyPagerFragmentAdapter(var list: List<ForSearchPager>, var manager: FragmentManager):FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return PagerSearchFragment.newInstance(list[position])
    }
}