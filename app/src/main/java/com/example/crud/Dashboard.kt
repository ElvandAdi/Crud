package com.example.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*

class Dashboard : AppCompatActivity(), View.OnClickListener {
    private lateinit var etNama : EditText
    private lateinit var etAlamat : EditText
    private lateinit var etJumlah : EditText
    private lateinit var etTanggal : EditText
    private lateinit var etKembali : EditText
    private lateinit var btnSave : Button
    private lateinit var listHtg : ListView
    private lateinit var ref: DatabaseReference
    private lateinit var htgList: MutableList<Hutang>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        ref = FirebaseDatabase.getInstance().getReference("Crud")

        etNama = findViewById(R.id.et_nama)
        etAlamat = findViewById(R.id.et_alamat)
        etJumlah = findViewById(R.id.et_jumlah)
        etTanggal = findViewById(R.id.et_tanggal)
        etKembali = findViewById(R.id.et_kembali)
        btnSave = findViewById(R.id.btn_save)
        listHtg = findViewById(R.id.lv_htg)
        btnSave.setOnClickListener(this)


        htgList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    htgList.clear()
                    for(h in p0.children){
                        val hutang = h.getValue(Hutang::class.java)
                        if (hutang != null) {
                            htgList.add(hutang)
                        }
                    }
                    val adapter = HutangAdapter(this@Dashboard, R.layout.item_htg, htgList)
                    listHtg.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    override fun onClick(p0: View?) {
        savedata()
    }

    private fun savedata(){
        val nama : String = etNama.text.toString().trim()
        val alamat : String = etAlamat.text.toString().trim()
        val jumlah : String = etJumlah.text.toString().trim()
        val tanggal : String = etTanggal.text.toString().trim()
        val kembali : String = etKembali.text.toString().trim()

        if (nama.isEmpty()){
            etNama.error = "Isi Nama"
            return
        }

        if (alamat.isEmpty()){
            etAlamat.error = "Isi Alamat"
            return
        }

        if (jumlah.isEmpty()){
            etJumlah.error = "Isi Jumlah Hutang"
            return
        }

        if (tanggal.isEmpty()){
            etTanggal.error = "Isi Tanggal"
            return
        }

        if (kembali.isEmpty()){
            etKembali.error = "Isi Tanggal Kembali"
            return
        }




        val htgId= ref.push().key

        val htg = Hutang(htgId!!,nama,alamat, jumlah, tanggal, kembali)

        if (htgId != null) {
            ref.child(htgId).setValue(htg).addOnCompleteListener{
                Toast.makeText(applicationContext, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            }
        }

    }
}