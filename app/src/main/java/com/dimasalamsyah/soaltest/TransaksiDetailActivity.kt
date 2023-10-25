package com.dimasalamsyah.soaltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimasalamsyah.soaltest.databinding.ActivityTransaksiDetailBinding
import com.dimasalamsyah.soaltest.databinding.ActivityTransaksiListHeaderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransaksiDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityTransaksiDetailBinding
    lateinit var adapter: TransaksiDetailAdapter
    lateinit var headers: TransaksiHeaderModel
    var details = mutableListOf<TransaksiDetailModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransaksiDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bundle = intent.extras
        if (bundle != null) {
            headers = intent.getSerializable("headers", TransaksiHeaderModel::class.java)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recylerView.layoutManager = layoutManager
        adapter = TransaksiDetailAdapter(details, object: TransaksiDetailClickListener{
            override fun onItemClicked(view: View, detail: TransaksiDetailModel) {

            }
        })
        binding.recylerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            binding.recylerView.context,
            layoutManager.orientation
        )
        binding.recylerView.addItemDecoration(dividerItemDecoration)

        NetworkConfig().getService()
            .getTransaksiDetail(headers.id!!)
            .enqueue(object : Callback<ResponseTransaksiDetailApi> {
                override fun onFailure(call: Call<ResponseTransaksiDetailApi>, t: Throwable) {
                    Toast.makeText(this@TransaksiDetailActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(
                    call: Call<ResponseTransaksiDetailApi>,
                    response: Response<ResponseTransaksiDetailApi>,
                ) {

                    var list = mutableListOf<TransaksiDetailModel>()
                    response.body()?.data?.forEach {
                        it?.let { it1 -> list.add(it1) }
                    }

                    details.clear()
                    details.addAll(list)
                    adapter.notifyDataSetChanged()
                }
            })
    }

}