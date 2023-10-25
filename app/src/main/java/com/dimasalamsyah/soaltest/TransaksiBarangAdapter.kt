package com.dimasalamsyah.soaltest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dimasalamsyah.soaltest.databinding.LayoutItemBarangBinding
import com.dimasalamsyah.soaltest.databinding.LayoutItemQtyBarangBinding

interface TransaksiBarangClickListener {
    fun onItemClicked(view: View, barang: BarangModel)
}

class TransaksiBarangAdapter (private val mList: List<BarangModel>, val listener: TransaksiBarangClickListener) : RecyclerView.Adapter<TransaksiBarangAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutItemQtyBarangBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemQtyBarangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.layout.setOnClickListener {
            listener.onItemClicked(it, mList[position])
        }

        with(holder){
            with(mList[position]){
                binding.tvKodeBarang.text = "Kode Barang: "+kodebarang
                binding.tvId.text = "ID: "+id.toString()
                binding.tvNamaBarang.text = "Nama Barang: "+namabarang
                binding.tvStokBarang.text = "Qty Barang: "+jumlahbarang .toString()
            }
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return mList.size
    }

    fun updateList(list: List<BarangModel>) {
//        mList.clear()
//        mList.addAll(list)
    }


}