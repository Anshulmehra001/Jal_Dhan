package com.example.jaldhan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jaldhan.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            // No real validation, just go to the next screen
            val farmerId = binding.editTextFarmerId.text.toString().ifEmpty { "12345" }

            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra("FARMER_ID", farmerId)
            startActivity(intent)
        }
    }
}