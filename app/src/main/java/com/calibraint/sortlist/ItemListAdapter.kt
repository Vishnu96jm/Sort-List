package com.calibraint.sortlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.calibraint.sortlist.databinding.ItemListBinding

class ItemListAdapter(private val itemList: MutableList<String>) :
    RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bindData(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    fun setData(newItems: List<String>, sortOption: SortOption) {
        val nameList = mutableListOf<String>()
        val symbolList = mutableListOf<String>()

        for (item in newItems){
            for (i in item.indices){
                if(item[i].code !in 65..90 && item[i].code !in 97..122
                    && item[i].code !in 48..57
                ){
                    symbolList.add(item)
                    break
                }else{
                    nameList.add(item)
                    break
                }
            }
        }
        val sortedItems = when (sortOption) {
            None -> newItems
            ByName -> nameList
            BySymbols -> symbolList
        }

        itemList.clear()
        itemList.addAll(sortedItems)
        notifyDataSetChanged()
    }

    inner class ItemListViewHolder(binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root){
        private val textValue: TextView = binding.listItem
        fun bindData(value : String){
            textValue.text = value
        }
    }
}