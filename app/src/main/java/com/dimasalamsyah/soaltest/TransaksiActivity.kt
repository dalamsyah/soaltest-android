package com.dimasalamsyah.soaltest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimasalamsyah.soaltest.databinding.ActivityTransaksiBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date


class TransaksiActivity : AppCompatActivity() {

    lateinit var binding: ActivityTransaksiBinding
    lateinit var adapter: TransaksiBarangAdapter
    var barangs = mutableListOf<BarangModel>()

    private val getResultBarang =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK){
                println(intent.extras?.isEmpty)

                var bundle = it.data?.extras
                var barang = it.data?.getSerializable( "barang", BarangModel::class.java)
                val qty = bundle?.getString("qty", "0")


                val check = barangs.filter { b ->
                    b.id == barang?.id
                }

                if (check.isEmpty()) {
                    barang?.jumlahbarang = qty?.toInt()
                    barang?.let {
                            it1 -> barangs.add(it1)
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@TransaksiActivity, "Duplikat", Toast.LENGTH_SHORT).show()
                }

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransaksiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())

        val bundle = intent.extras

        binding.tvType.text = "Barang "+bundle?.getString("type")
        binding.btnPilihBarang.setOnClickListener {
            val intent = Intent(this, MasterBarangActivity::class.java)
            intent.putExtra("type", "searchbarang")
            getResultBarang.launch(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recylerView.layoutManager = layoutManager
        adapter = TransaksiBarangAdapter(barangs, object: TransaksiBarangClickListener {
            override fun onItemClicked(view: View, barang: BarangModel) {
                val builder = AlertDialog.Builder(this@TransaksiActivity)
                builder.setTitle("Hapus")

                builder.setPositiveButton("Ya") { dialog, which ->
                    barangs.remove(barang)
                    adapter.notifyDataSetChanged()
                }

                builder.setNegativeButton("Batal") { dialog, which ->

                }
                builder.show()

            }
        })
        binding.recylerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            binding.recylerView.context,
            layoutManager.orientation
        )
        binding.recylerView.addItemDecoration(dividerItemDecoration)

        binding.btnSubmit.setOnClickListener {

            if (binding.etNomorTransaksi.text.isEmpty()) {
                Toast.makeText(this@TransaksiActivity, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val jsonArray = JSONArray()
            try {
                for (barang in barangs) {
                    val jsonObject = JSONObject()
                    jsonObject.put("jumlahbarang", barang.jumlahbarang)
                    jsonObject.put("stokbarang", barang.stokbarang)
                    jsonObject.put("updatedAt", barang.updatedAt)
                    jsonObject.put("createdAt", barang.createdAt)
                    jsonObject.put("id", barang.id)
                    jsonObject.put("kodebarang", barang.kodebarang)
                    jsonObject.put("namabarang", barang.namabarang)
                    jsonArray.put(jsonObject)
                }
                Log.e("JSONArray", jsonArray.toString())
            } catch (jse: JSONException) {
                jse.printStackTrace()
            }

            NetworkConfig().getService()
                .postTransaksi(jsonArray.toString(), binding.etNomorTransaksi.text.toString(), intent.extras?.getString("type")!!)
                .enqueue(object : Callback<ResponseApi> {
                    override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                        Toast.makeText(this@TransaksiActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(
                        call: Call<ResponseApi>,
                        response: Response<ResponseApi>,
                    ) {
                        Toast.makeText(this@TransaksiActivity, "success", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                })
        }

    }

}