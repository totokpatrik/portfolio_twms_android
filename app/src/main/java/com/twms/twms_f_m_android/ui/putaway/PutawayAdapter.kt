package com.twms.twms_f_m_android.ui.putaway

import android.annotation.SuppressLint
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.twms.twms_f_m_android.data.model.Putaway
import com.twms.twms_f_m_android.databinding.AdapterPutawayBinding

class PutawayAdapter: RecyclerView.Adapter<MainViewHolder>() {

    private var putaways = mutableListOf<Putaway>()

    @SuppressLint("NotifyDataSetChanged")
    fun setPutawayList(putaways: List<Putaway>) {
        this.putaways = putaways.toMutableList()
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((Putaway) -> Unit)? = null
    fun setOnItemClickListener(listener: (Putaway) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterPutawayBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val putaway = putaways[position]

        holder.binding.otfPalletNumber.editText?.text = SpannableStringBuilder(putaway.inventory.palletNumber)
        holder.binding.otfLocation.editText?.text = SpannableStringBuilder(putaway.sourcLocation?.name)

        holder.binding.itemLayout.setOnClickListener{
            onItemClickListener?.let {
                it(putaway)
            }
        }
    }

    override fun getItemCount(): Int {
        return putaways.size
    }
}
class MainViewHolder(val binding: AdapterPutawayBinding) : RecyclerView.ViewHolder(binding.root)