package com.example.room

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.room.database.daftarBelanja
import com.example.room.database.daftarBelanjaDB
import com.example.room.database.historyBarang
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var  DB : daftarBelanjaDB
    private lateinit var  adapterDaftar: adapterDaftar
    private var arDaftar : MutableList<daftarBelanja> = mutableListOf()
    private lateinit var adapterHistory: adapterHistory
    private var arHistory: MutableList<historyBarang> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        adapterHistory = adapterHistory(arHistory)
        val _rvHistory = findViewById<RecyclerView>(R.id.rvHistory) // Pastikan ID RV untuk history sesuai
        _rvHistory.layoutManager = LinearLayoutManager(this)
        _rvHistory.adapter = adapterHistory

        DB = daftarBelanjaDB.getDatabase(this)

        val _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)

        _fabAdd.setOnClickListener {
            startActivity(Intent(this, tambahDaftar::class.java))
        }
        adapterDaftar = adapterDaftar(arDaftar)
        var _rvDaftar = findViewById<RecyclerView>(R.id.rvDaftar)
        _rvDaftar.layoutManager = LinearLayoutManager(this)
        _rvDaftar.adapter = adapterDaftar

        adapterDaftar.setOnItemClickCallback(
            object : adapterDaftar.OnItemClickCallback {
                override fun delData(dtBelanja: daftarBelanja) {
                    CoroutineScope(Dispatchers.IO).async {
                        DB.daftarBelanjaDAO().delete(dtBelanja)
                        val daftarBelanja = DB.daftarBelanjaDAO().selectAll()
                        withContext(Dispatchers.Main) {
                            adapterDaftar.isiData(daftarBelanja)
                        }
                    }
                }

                override fun dataDone(dtBelanja: daftarBelanja) {
                    CoroutineScope(Dispatchers.IO).async {
                        DB.daftarBelanjaDAO().delete(dtBelanja)
                        val historyItem = historyBarang(
                            tanggal = dtBelanja.tanggal,
                            item = dtBelanja.item,
                            jumlah = dtBelanja.jumlah
                        )
                        DB.historyBarangDAO().insert(historyItem)
                        val daftarBelanja = DB.daftarBelanjaDAO().selectAll()
                        val history = DB.historyBarangDAO().selectAll()
                        withContext(Dispatchers.Main) {
                            adapterDaftar.isiData(daftarBelanja)
                            adapterHistory.isiData(history)
                        }
                    }
                }
            }
        )


    }

    override fun onStart() {
        super.onStart()
        super.onStart()
        CoroutineScope(Dispatchers.Main).async{
            val daftarBelanja = DB.daftarBelanjaDAO().selectAll()
            adapterDaftar.isiData(daftarBelanja)
            Log.d("data ROOM", daftarBelanja.toString())
        }
        CoroutineScope(Dispatchers.Main).async{
            val history = DB.historyBarangDAO().selectAll()
            adapterHistory.isiData(history)
            Log.d("data ROOM", history.toString())
        }
    }
}