package com.example.jaldhan

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.jaldhan.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    // Modern way to handle permissions
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action
                openCamera()
            } else {
                // Explain to the user that the feature is unavailable
                Toast.makeText(this, "Camera permission is required to log data.", Toast.LENGTH_SHORT).show()
            }
        }

    // Modern way to handle the camera result
    private val startCameraActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Photo was taken successfully!
                // FAKE the data processing
                Toast.makeText(this, "Success! Data saved with location.", Toast.LENGTH_LONG).show()

                // Update the dashboard UI
                binding.farmStatusTextView.text = "Farm Status: Draining"
                binding.lastReadingTextView.text = "Last Reading: Just now"
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get Farmer ID from LoginActivity and display it
        val farmerId = intent.getStringExtra("FARMER_ID")
        binding.welcomeTextView.text = "Welcome, Farmer $farmerId!"

        // Set listener for the camera Floating Action Button
        binding.cameraFab.setOnClickListener {
            checkCameraPermissionAndOpenCamera()
        }
    }

    private fun checkCameraPermissionAndOpenCamera() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                openCamera()
            }
            else -> {
                // You can directly ask for the permission.
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startCameraActivity.launch(cameraIntent)
    }
}