package com.mauriciovera.billeteravirtualkroom.view

import android.os.Bundle
//import android.util.Log
//import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mauriciovera.billeteravirtualkroom.R
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.mauriciovera.billeteravirtualkroom.R
import com.mauriciovera.billeteravirtualkroom.databinding.ActivityLoginSignupBinding
//import com.mauriciovera.billeteravirtualkroom.databinding.ActivityMainBinding

class LoginSignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginSignupBinding //xml
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.soft_blue)
    }
}