package uz.evkalipt.sevenmodullesson121.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.evkalipt.sevenmodullesson121.databinding.ItemForRvPagerSearchBinding
import uz.evkalipt.sevenmodullesson121.models.MeaningOne

class RvAdapterPagerSearch(var list: List<MeaningOne>):RecyclerView.Adapter<RvAdapterPagerSearch.Vh>(){

    inner class Vh(var itemForRvPagerSearchBinding: ItemForRvPagerSearchBinding):RecyclerView.ViewHolder(itemForRvPagerSearchBinding.root){
        fun onBind(meaningOne: MeaningOne, position: Int){
            itemForRvPagerSearchBinding.number.text = (position+1).toString()
            itemForRvPagerSearchBinding.noun.text = meaningOne.partOfSpeech
            if (meaningOne.definition.length>25) {
                itemForRvPagerSearchBinding.descriptionTop.text =
                    meaningOne.definition.substring(0, 25)
                itemForRvPagerSearchBinding.description.text = meaningOne.definition.substring(25)
            }else{
                itemForRvPagerSearchBinding.descriptionTop.text = meaningOne.definition
            }
            itemForRvPagerSearchBinding.example.text = meaningOne.example
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemForRvPagerSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}