package com.dimasalamsyah.soaltest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dimasalamsyah.soaltest.databinding.LayoutItemHeaderTransaksiBinding

interface TransaksiHeaderClickListener {
    fun onItemClicked(view: View, barang: TransaksiHeaderModel)
}

class TransaksiHeaderAdapter (private val mList: List<TransaksiHeaderModel>, val listener: TransaksiHeaderClickListener) : RecyclerView.Adapter<TransaksiHeaderAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutItemHeaderTransaksiBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemHeaderTransaksiBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.header = mList[position]
        holder.binding.layout.setOnClickListener {
            listener.onItemClicked(it, mList[position])
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

}