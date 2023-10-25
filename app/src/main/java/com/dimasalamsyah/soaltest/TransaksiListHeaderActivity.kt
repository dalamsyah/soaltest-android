package com.dimasalamsyah.soaltest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimasalamsyah.soaltest.databinding.ActivityMasterBarangBinding
import com.dimasalamsyah.soaltest.databinding.ActivityTransaksiListHeaderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransaksiListHeaderActivity : AppCompatActivity() {

    lateinit var binding: ActivityTransaksiListHeaderBinding
    lateinit var adapter: TransaksiHeaderAdapter
    var headers = mutableListOf<TransaksiHeaderModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransaksiListHeaderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val layoutManager = LinearLayoutManager(this)
        binding.recylerView.layoutManager = layoutManager
        adapter = TransaksiHeaderAdapter(headers, object: TransaksiHeaderClickListener{
            override fun onItemClicked(view: View, header: TransaksiHeaderModel) {
                val intent = Intent(this@TransaksiListHeaderActivity, TransaksiDetailActivity::class.java)
                intent.putExtra("headers", header)
                startActivity(intent)
            }
        })
        binding.recylerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            binding.recylerView.context,
            layoutManager.orientation
        )
        binding.recylerView.addItemDecoration(dividerItemDecoration)


        NetworkConfig().getService()
            .getTransaksi()
            .enqueue(object : Callback<ResponseTransaksiHeaderApi> {
                override fun onFailure(call: Call<ResponseTransaksiHeaderApi>, t: Throwable) {
                    Toast.makeText(this@TransaksiListHeaderActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(
                    call: Call<ResponseTransaksiHeaderApi>,
                    response: Response<ResponseTransaksiHeaderApi>,
                ) {

                    var list = mutableListOf<TransaksiHeaderModel>()
                    response.body()?.data?.forEach {
                        it?.let { it1 -> list.add(it1) }
                    }

                    headers.clear()
                    headers.addAll(list)
                    adapter.notifyDataSetChanged()
                }
            })

    }
}