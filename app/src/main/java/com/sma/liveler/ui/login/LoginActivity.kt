package com.sma.liveler.ui.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sma.liveler.R
import com.sma.liveler.databinding.ActivityLoginBinding
import com.sma.liveler.repository.UserRepository
import com.sma.liveler.ui.home.HomeActivity
import com.sma.liveler.ui.registration.RegisterActivity
import com.sma.liveler.utils.Utils
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    private val REQUEST_PERMISSION = 100

    private lateinit var binding: ActivityLoginBinding
    private var errorSnackbar: Snackbar? = null

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: LoginViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(
                    this@LoginActivity,
                    UserRepository(this@LoginActivity)
                ) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel

        viewModel.errorMessage.observe(this, Observer {
            Utils.alert(this, it)
        })
        viewModel.loadingVisibility.observe(this, Observer {
            binding.layoutLoading.visibility = it
        })
        viewModel.success.observe(this, Observer {
            if (it) {
                val homeIntent = Intent(this, HomeActivity::class.java)
                startActivity(homeIntent)
                finish()
            }
        })

        binding.btnLogin.setOnClickListener {
            viewModel.onclickLogin(
                binding.edtUserName.text.toString(),
                binding.edtPassword.text.toString()
            )
        }

        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Storage permission has not been granted.
                requestPermission(REQUEST_PERMISSION)
            }
        }
    }

    private fun requestPermission(id: Int) {
        Timber.d("Storage permission has NOT been granted. Requesting permission.")
        // Storage permission has not been granted yet. Request it directly.
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            id
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.size == 3
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED
            ) {
            } else if (grantResults.size == 2
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
            } else {
                Toast.makeText(this, "Please allow permission to continue.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}