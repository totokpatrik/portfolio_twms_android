package com.twms.twms_f_m_android.ui.inbound_shipment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.twms.twms_f_m_android.data.model.ReceiveLine
import com.twms.twms_f_m_android.databinding.AdapterInboundShipmentBinding

class InboundShipmentAdapter: RecyclerView.Adapter<MainViewHolderInboundShipment>() {

    private var inboundShipments = mutableListOf<ReceiveLine>()

    @SuppressLint("NotifyDataSetChanged")
    fun setInboundShipmentList(movies: List<ReceiveLine>) {
        this.inboundShipments = movies.toMutableList()
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((ReceiveLine) -> Unit)? = null
    fun setOnItemClickListener(listener: (ReceiveLine) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolderInboundShipment {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterInboundShipmentBinding.inflate(inflater, parent, false)
        return MainViewHolderInboundShipment(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainViewHolderInboundShipment, position: Int) {
        val inboundShipment = inboundShipments[position]
        holder.binding.tvInboundOrder.text = inboundShipment.inboundOrderName
        holder.binding.tvItem.text = inboundShipment.item.name
        holder.binding.tvItemDescription.text = inboundShipment.item.description
        holder.binding.tvReceivedPercentage.text = ((inboundShipment.receivedQuantity.toDouble() / inboundShipment.expectedQuantity.toDouble()) * 100).toString()
        holder.binding.lpiReceived.progress = ((inboundShipment.receivedQuantity.toDouble() / inboundShipment.expectedQuantity.toDouble()) * 100).toInt()
        holder.binding.tvReceivedSummary.text = "( " + inboundShipment.receivedQuantity + " of " + inboundShipment.expectedQuantity + " )"

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
class MainViewHolderInboundShipment(val binding: AdapterInboundShipmentBinding) : RecyclerView.ViewHolder(binding.root) {
}