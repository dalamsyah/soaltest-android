package com.dimasalamsyah.soaltest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimasalamsyah.soaltest.databinding.ActivityMasterBarangBinding
import com.dimasalamsyah.soaltest.databinding.LayoutStokDialogBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController.getContext


class MasterBarangActivity : AppCompatActivity() {
    lateinit var binding: ActivityMasterBarangBinding
    lateinit var adapter: BarangAdapter
    var barangs = mutableListOf<BarangModel>()

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK){
                getData()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMasterBarangBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bundle = intent.extras

        binding.btnAddBarang.setOnClickListener {
            var intent = Intent(this, MasterBarangAddActivity::class.java)
            getResult.launch(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recylerView.layoutManager = layoutManager
        adapter = BarangAdapter(barangs, object: BarangClickListener{
            override fun onItemClicked(view: View, barang: BarangModel) {

              if (bundle != null) {
                  val builder = AlertDialog.Builder(this@MasterBarangActivity)
                  builder.setTitle("Masukan Qty Barang")

                  val view = layoutInflater.inflate(R.layout.layout_stok_dialog,null)
                  val editText = view.findViewById<EditText>(R.id.etQtyBarang)
                  builder.setView(view)

                  builder.setPositiveButton("OK") { dialog, which ->
                      val intent = Intent()
                      intent.putExtra("barang", barang)
                      intent.putExtra("qty", editText.text.toString())
                      setResult(RESULT_OK, intent)
                      finish()
                  }

                  builder.setNegativeButton("Batal") { dialog, which ->
                      Toast.makeText(applicationContext, "Batal", Toast.LENGTH_SHORT).show()
                  }
                  builder.show()
              } else {
                  val builder = AlertDialog.Builder(this@MasterBarangActivity)
                  builder.setTitle("Action")

                  builder.setPositiveButton("Edit") { dialog, which ->
                      var intent = Intent(this@MasterBarangActivity, MasterBarangAddActivity::class.java)
                      intent.putExtra("barang", barang)
                      getResult.launch(intent)
                  }

                  builder.setNegativeButton("Hapus") { dialog, which ->
                      NetworkConfig().getService()
                          .deleteBarang(barang.id!!)
                          .enqueue(object : Callback<ResponseApi> {
                              override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                                  Toast.makeText(this@MasterBarangActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                              }
                              override fun onResponse(
                                  call: Call<ResponseApi>,
                                  response: Response<ResponseApi>
                              ) {
                                  getData()
                              }
                          })
                  }

                  builder.setNeutralButton("Batal") { dialog, which ->

                  }

                  builder.show()
              }

            }
        })
        binding.recylerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            binding.recylerView.context,
            layoutManager.orientation
        )
        binding.recylerView.addItemDecoration(dividerItemDecoration)

        getData()


    }

    private fun getData() {
        NetworkConfig().getService()
            .getBarang()
            .enqueue(object : Callback<ResponseApi> {
                override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                    Toast.makeText(this@MasterBarangActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(
                    call: Call<ResponseApi>,
                    response: Response<ResponseApi>,
                ) {
                    var list = mutableListOf<BarangModel>()
                    response.body()?.data?.forEach {
                        it?.let { it1 -> list.add(it1) }
                    }

                    barangs.clear()
                    barangs.addAll(list)
                    adapter.notifyDataSetChanged()
                }
            })
    }

}