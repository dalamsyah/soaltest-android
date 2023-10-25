package com.dimasalamsyah.soaltest

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.dimasalamsyah.soaltest.databinding.ActivityMasterBarangAddBinding
import com.dimasalamsyah.soaltest.databinding.ActivityMasterBarangBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class MasterBarangAddActivity : AppCompatActivity() {
    lateinit var binding: ActivityMasterBarangAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMasterBarangAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnUpdate.visibility = View.GONE

        val bundle = intent.extras
        var idBarang = 0
        if (bundle != null) {
            val barang = intent.getSerializable("barang", BarangModel::class.java)
            binding.etKodeBarang.text = barang.kodebarang?.toEditable() ?: "".toEditable()
            binding.etNamaBarang.text = barang.namabarang?.toEditable() ?: "".toEditable()
            binding.etStokBarang.text = barang.stokbarang?.toString()?.toEditable() ?: "".toEditable()

            binding.btnSave.visibility = View.GONE
            binding.btnUpdate.visibility = View.VISIBLE
            idBarang = barang.id!!
        }

        binding.btnSave.setOnClickListener {

            if (
                binding.etKodeBarang.text.isEmpty() ||
                binding.etNamaBarang.text.isEmpty() ||
                binding.etStokBarang.text.isEmpty()
            ) {
                Toast.makeText(this@MasterBarangAddActivity, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            NetworkConfig().getService()
                .postBarang(binding.etKodeBarang.text.toString(), binding.etNamaBarang.text.toString(), binding.etStokBarang.text.toString().toInt())
                .enqueue(object : Callback<ResponseApi> {
                    override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                        Toast.makeText(this@MasterBarangAddActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(
                        call: Call<ResponseApi>,
                        response: Response<ResponseApi>
                    ) {
                        Toast.makeText(this@MasterBarangAddActivity, "success", Toast.LENGTH_SHORT).show()
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                })
        }

        binding.btnUpdate.setOnClickListener {

            if (
                binding.etKodeBarang.text.isEmpty() ||
                binding.etNamaBarang.text.isEmpty() ||
                binding.etStokBarang.text.isEmpty()
            ) {
                Toast.makeText(this@MasterBarangAddActivity, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            NetworkConfig().getService()
                .updateBarang(idBarang, binding.etKodeBarang.text.toString(), binding.etNamaBarang.text.toString(), binding.etStokBarang.text.toString().toInt(), "PATCH")
                .enqueue(object : Callback<ResponseApi> {
                    override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                        Toast.makeText(this@MasterBarangAddActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(
                        call: Call<ResponseApi>,
                        response: Response<ResponseApi>
                    ) {
                        Toast.makeText(this@MasterBarangAddActivity, "success", Toast.LENGTH_SHORT).show()
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                })
        }
    }
}