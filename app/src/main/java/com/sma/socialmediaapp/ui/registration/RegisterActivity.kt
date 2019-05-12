package com.sma.socialmediaapp.ui.registration

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sma.socialmediaapp.R
import com.sma.socialmediaapp.databinding.ActivityRegisterBinding
import com.sma.socialmediaapp.injection.ViewModelFactory
import com.sma.socialmediaapp.ui.home.HomeActivity
import com.sma.socialmediaapp.ui.login.LoginActivity
import com.sma.socialmediaapp.utils.Utils

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(RegisterViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.success.observe(this, Observer { success ->
            if (success) {
                registrationSuccess()
            } else Utils.alert(this, getString(R.string.error_registration))
        })

        viewModel.loading.observe(this, Observer { isVisible ->
            binding.layoutLoading.visibility = isVisible
        })

        binding.tvLogin.setOnClickListener({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        })

        binding.btnRegister.setOnClickListener({

            if (TextUtils.isEmpty(binding.edtFirstName.text)) {
                binding.edtFirstName.error = "Invalid first name"

            } else if (TextUtils.isEmpty(binding.edtMiddleName.text)) {
                binding.edtMiddleName.error = "Invalid middle name"

            } else if (TextUtils.isEmpty(binding.edtLastName.text)) {
                binding.edtLastName.error = "Invalid last name"
            } else if (TextUtils.isEmpty(binding.edtPhone.text)) {
                binding.edtPhone.error = "Invalid phone"
            } else if (TextUtils.isEmpty(binding.edtOtp.text)) {
                binding.edtOtp.error = "Invalid email"
            } else if (TextUtils.isEmpty(binding.edtPassword.text)) {
                binding.edtPassword.error = "Invalid password"
            } else {

                viewModel.register(
                    binding.edtFirstName.text.toString(),
                    binding.edtOtp.text.toString(),
                    binding.edtPassword.text.toString()
                )
            }
        })
    }

    private fun registrationSuccess() {
        val dialog = AlertDialog.Builder(
            this,
            R.style.Theme_App_Light_Dialog_NoActionBar
        )
            .setMessage("Registration completed successfully")
            .setCancelable(false)
            .setPositiveButton(getString(R.string.btn_ok)) { dialogInterface, i ->
                dialogInterface.cancel()
                dialogInterface.dismiss()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            .create()

        if (dialog != null && !dialog.isShowing)
            dialog.show()
    }
}
