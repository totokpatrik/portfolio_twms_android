package com.twms.twms_f_m_android.ui.loading

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.twms.twms_f_m_android.data.model.Load
import com.twms.twms_f_m_android.databinding.AdapterLoadingBinding

class LoadingAdapter: RecyclerView.Adapter<MainViewHolder>() {

    private var loads = mutableListOf<Load>()

    @SuppressLint("NotifyDataSetChanged")
    fun setLoadList(loads: List<Load>) {
        this.loads = loads.toMutableList()
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((Load) -> Unit)? = null
    fun setOnItemClickListener(listener: (Load) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterLoadingBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val load = loads[position]

        holder.binding.tvOutboundShipment.text = load.outboundShipment.name
        holder.binding.tvLocation.text = load.outboundShipment.location.name

        holder.binding.itemLayout.setOnClickListener{
            onItemClickListener?.let {
                it(load)
            }
        }
    }

    override fun getItemCount(): Int {
        return loads.size
    }
}
class MainViewHolder(val binding: AdapterLoadingBinding) : RecyclerView.ViewHolder(binding.root)