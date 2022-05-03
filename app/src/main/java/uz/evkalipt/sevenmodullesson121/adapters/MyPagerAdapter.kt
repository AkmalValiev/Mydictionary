package uz.evkalipt.sevenmodullesson121.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.item_pager.view.*
import uz.evkalipt.sevenmodullesson121.R
import uz.evkalipt.sevenmodullesson121.models.Word

class MyPagerAdapter(var list: List<Word>):PagerAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var inflater = LayoutInflater.from(container.context)
        var itemView = inflater.inflate(R.layout.item_pager, container, false)
        itemView.title_splash.text = list[position].title
        itemView.description.text = list[position].description
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}