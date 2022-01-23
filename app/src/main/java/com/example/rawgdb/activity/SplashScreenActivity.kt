package com.example.rawgdb.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.rawgdb.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val context = this

        val actionBar = supportActionBar
        actionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        },2500)

    }
}