package uz.evkalipt.sevenmodullesson121.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.evkalipt.sevenmodullesson121.databinding.ItemForRvBinding
import uz.evkalipt.sevenmodullesson121.entities.Word3

class RvAdapter(var list: List<Word3>, var myOnClick: MyOnClick):RecyclerView.Adapter<RvAdapter.Vh>() {

    inner class Vh(var itemForRvBinding: ItemForRvBinding):RecyclerView.ViewHolder(itemForRvBinding.root){
        fun onBind(word3: Word3, position: Int){
            itemForRvBinding.wordItem.text = word3.word
            itemForRvBinding.transcription.text = word3.phonetic
            if (word3.save==1){
                itemForRvBinding.save1.visibility = View.VISIBLE
            }else{
                itemForRvBinding.save1.visibility = View.INVISIBLE
            }
            itemForRvBinding.save0.setOnClickListener {
                myOnClick.clickSave(word3, position)
            }
            itemForRvBinding.save1.setOnClickListener {
                myOnClick.clickSave2(word3, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemForRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface MyOnClick{
        fun clickSave(word3: Word3, position: Int)
        fun clickSave2(word3: Word3, position: Int)
    }

}