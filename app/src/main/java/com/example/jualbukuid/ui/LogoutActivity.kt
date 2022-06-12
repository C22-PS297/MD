package com.example.jualbukuid.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.jualbukuid.MainActivity
import com.example.jualbukuid.databinding.ActivityLogoutBinding
import com.example.jualbukuid.models.UserViewModel
import com.example.jualbukuid.models.ViewModelFactory
import com.example.jualbukuid.models.UserPreference

private val Context.data: DataStore<Preferences> by preferencesDataStore(name = "data")

class LogoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogoutBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(data), this)
        )[UserViewModel::class.java]

        binding.btnNo.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnYes.setOnClickListener {
            userViewModel.logout()
            val logout = Intent(this, LoginActivity::class.java)
            logout.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logout)
        }
    }
}