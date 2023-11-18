package com.twms.twms_f_m_android.ui.loading

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.twms.twms_f_m_android.data.model.Inventory
import com.twms.twms_f_m_android.databinding.AdapterLoadingDetailBinding

class LoadingDetailAdapter: RecyclerView.Adapter<MainViewHolderDetail>() {

    private var inventories = mutableListOf<Inventory>()

    @SuppressLint("NotifyDataSetChanged")
    fun setInventoryList(inventories: List<Inventory>) {
        this.inventories = inventories.toMutableList()
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((Inventory) -> Unit)? = null
    fun setOnItemClickListener(listener: (Inventory) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolderDetail {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterLoadingDetailBinding.inflate(inflater, parent, false)
        return MainViewHolderDetail(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolderDetail, position: Int) {
        val inventory = inventories[position]

        holder.binding.tvPalletNumber.text = inventory.palletNumber
        if (inventory.isLoaded) {
            holder.binding.imgDone.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return inventories.size
    }
}
class MainViewHolderDetail(val binding: AdapterLoadingDetailBinding) : RecyclerView.ViewHolder(binding.root)