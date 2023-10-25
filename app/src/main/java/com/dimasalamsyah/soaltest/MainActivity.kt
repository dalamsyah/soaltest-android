package com.dimasalamsyah.soaltest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import com.dimasalamsyah.soaltest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnMasterBarang.setOnClickListener {
            startActivity(Intent(this, MasterBarangActivity::class.java))
        }

        binding.btnTransaksiMasuk.setOnClickListener {
            val intent = Intent(this, TransaksiActivity::class.java)
            intent.putExtra("type", "masuk")
            startActivity(intent)
        }

        binding.btnTransaksiKeluar.setOnClickListener {
            val intent = Intent(this, TransaksiActivity::class.java)
            intent.putExtra("type", "keluar")
            startActivity(intent)
        }

        binding.btnListTransaksi.setOnClickListener {
            val intent = Intent(this, TransaksiListHeaderActivity::class.java)
            startActivity(intent)
        }
    }
}