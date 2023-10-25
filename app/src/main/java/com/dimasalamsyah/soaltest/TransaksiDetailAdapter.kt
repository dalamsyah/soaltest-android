package com.dimasalamsyah.soaltest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dimasalamsyah.soaltest.databinding.LayoutItemDetailTransaksiBinding

interface TransaksiDetailClickListener {
    fun onItemClicked(view: View, barang: TransaksiDetailModel)
}

class TransaksiDetailAdapter (private val mList: List<TransaksiDetailModel>, val listener: TransaksiDetailClickListener) : RecyclerView.Adapter<TransaksiDetailAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutItemDetailTransaksiBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemDetailTransaksiBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.detail = mList[position]
        holder.binding.layout.setOnClickListener {
            listener.onItemClicked(it, mList[position])
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

}