package com.twms.twms_f_m_android.ui.receiving

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.twms.twms_f_m_android.data.model.InboundShipment
import com.twms.twms_f_m_android.databinding.AdapterReceivingBinding

class ReceivingAdapter: RecyclerView.Adapter<MainViewHolder>() {

    private var inboundShipments = mutableListOf<InboundShipment>()

    @SuppressLint("NotifyDataSetChanged")
    fun setInboundShipmentList(movies: List<InboundShipment>) {
        this.inboundShipments = movies.toMutableList()
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((InboundShipment) -> Unit)? = null
    fun setOnItemClickListener(listener: (InboundShipment) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterReceivingBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val inboundShipment = inboundShipments[position]
        holder.binding.tvInboundShipment.text = inboundShipment.name
        holder.binding.tvLocation.text = inboundShipment.location!!.name

        holder.binding.itemLayout.setOnClickListener{
            onItemClickListener?.let {
                it(inboundShipment)
            }
        }
    }

    override fun getItemCount(): Int {
        return inboundShipments.size
    }
}
class MainViewHolder(val binding: AdapterReceivingBinding) : RecyclerView.ViewHolder(binding.root) {
}