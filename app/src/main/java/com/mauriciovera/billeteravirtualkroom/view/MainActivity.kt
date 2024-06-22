package com.mauriciovera.billeteravirtualkroom.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mauriciovera.billeteravirtualkroom.R
import com.mauriciovera.billeteravirtualkroom.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.soft_blue)

        window.navigationBarColor = getColor(R.color.soft_blue)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            val intent = Intent(this@MainActivity, LoginSignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}