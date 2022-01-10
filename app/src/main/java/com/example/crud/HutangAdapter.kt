package com.example.crud

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class HutangAdapter(val mCtx : Context, val layoutResId : Int, val htgList :List<Hutang> ) :ArrayAdapter<Hutang> (mCtx, layoutResId, htgList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(layoutResId, null)

        val tvNama : TextView = view.findViewById(R.id.tv_nama)
        val tvAlamat : TextView = view.findViewById(R.id.tv_alamat)
        val tvJumlah : TextView = view.findViewById(R.id.tv_jumlah)
        val tvTanggal : TextView = view.findViewById(R.id.tv_tanggal)
        val tvKembali : TextView = view.findViewById(R.id.tv_kembali)
        val tvEdit : TextView = view.findViewById(R.id.tv_edit)

        val hutang = htgList[position]

        tvEdit.setOnClickListener{
            showUpdateDialog(hutang)
        }

        tvNama.text = hutang.nama
        tvAlamat.text = hutang.alamat
        tvJumlah.text = hutang.jumlah
        tvTanggal.text = hutang.tanggal
        tvKembali.text = hutang.kembali

        return view
    }

    private fun showUpdateDialog(hutang: Hutang) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Edit Data")

        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.update_dialog, null)

        val etNama = view.findViewById<EditText>(R.id.et_nama)
        val etAlamat = view.findViewById<EditText>(R.id.et_alamat)
        val etJumlah = view.findViewById<EditText>(R.id.et_jumlah)
        val etTanggal = view.findViewById<EditText>(R.id.et_tanggal)
        val etKembali = view.findViewById<EditText>(R.id.et_kembali)

        etNama.setText(hutang.nama)
        etAlamat.setText(hutang.alamat)
        etJumlah.setText(hutang.jumlah)
        etTanggal.setText(hutang.tanggal)
        etKembali.setText(hutang.kembali)

        builder.setView(view)

        builder.setPositiveButton("Update"){p0,p1 ->
            val dbMhs = FirebaseDatabase.getInstance().getReference("Crud")

            val nama = etNama.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            val jumlah = etJumlah.text.toString().trim()
            val tanggal = etTanggal.text.toString().trim()
            val kembali = etKembali.text.toString().trim()
            if(nama.isEmpty()){
                etNama.error = "Mohon nama di isi"
                etNama.requestFocus()
                return@setPositiveButton
            }
            if(alamat.isEmpty()){
                etAlamat.error = "Mohon alamat di isi"
                etAlamat.requestFocus()
                return@setPositiveButton
            }

            if(jumlah.isEmpty()){
                etJumlah.error = "Mohon jumlah di isi"
                etJumlah.requestFocus()
                return@setPositiveButton
            }

            if(tanggal.isEmpty()){
                etTanggal.error = "Mohon tanggal di isi"
                etTanggal.requestFocus()
                return@setPositiveButton
            }

            if(kembali.isEmpty()){
                etKembali.error = "Mohon tanggal pengembalian di isi"
                etKembali.requestFocus()
                return@setPositiveButton
            }


            val hutang = Hutang(hutang.id, nama, alamat, jumlah, tanggal, kembali)


            dbMhs.child(hutang.id).setValue(hutang)

            Toast.makeText(mCtx, "Data berhasil di update", Toast.LENGTH_SHORT).show()

        }

        builder.setNeutralButton("Cancel"){p0,p1 ->

        }

        builder.setNegativeButton("Delete"){p0,p1 ->

            val dbMhs =  FirebaseDatabase.getInstance().getReference("Crud").child(hutang.id)

            dbMhs.removeValue()

            Toast.makeText(mCtx, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()

        }

        val alert = builder.create()
        alert.show()
    }




}