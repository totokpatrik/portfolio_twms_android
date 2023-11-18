package com.twms.twms_f_m_android.ui.picking

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.twms.twms_f_m_android.data.model.OutboundShipment
import com.twms.twms_f_m_android.databinding.AdapterPickingBinding

class PickingAdapter: RecyclerView.Adapter<MainViewHolder>() {

    private var outboundShipments = mutableListOf<OutboundShipment>()

    @SuppressLint("NotifyDataSetChanged")
    fun setOutboundShipmentList(movies: List<OutboundShipment>) {
        this.outboundShipments = movies.toMutableList()
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((OutboundShipment) -> Unit)? = null
    fun setOnItemClickListener(listener: (OutboundShipment) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterPickingBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val outboundShipment = outboundShipments[position]
        holder.binding.tvOutboundShipment.text = outboundShipment.name
        holder.binding.tvLocation.text = outboundShipment.location.name

        holder.binding.itemLayout.setOnClickListener{
            onItemClickListener?.let {
                it(outboundShipment)
            }
        }
    }

    override fun getItemCount(): Int {
        return outboundShipments.size
    }
}
class MainViewHolder(val binding: AdapterPickingBinding) : RecyclerView.ViewHolder(binding.root) {
}