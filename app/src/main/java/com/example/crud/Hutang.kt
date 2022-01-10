package com.example.crud

data class Hutang(
    val id : String,
    val nama : String,
    val alamat : String,
    val jumlah : String,
    val tanggal : String,
    val kembali : String
){
    constructor(): this("","","", "", "", ""){

    }
}