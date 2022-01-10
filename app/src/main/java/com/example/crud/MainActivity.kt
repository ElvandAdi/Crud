package com.example.crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        btnLogin = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        val intent = Intent(this@MainActivity, Dashboard::class.java)
        startActivity(intent)
    }
}