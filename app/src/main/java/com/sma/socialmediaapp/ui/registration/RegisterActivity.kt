package com.sma.socialmediaapp.ui.registration

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.sma.socialmediaapp.R
import com.sma.socialmediaapp.databinding.ActivityRegisterBinding
import com.sma.socialmediaapp.injection.ViewModelFactory
import com.sma.socialmediaapp.ui.home.HomeActivity
import com.sma.socialmediaapp.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(RegisterViewModel::class.java)
        binding.viewModel = viewModel

        binding.tvLogin.setOnClickListener({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
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
                binding.edtOtp.error = "Invalid Otp"
            } else if (TextUtils.isEmpty(binding.edtPassword.text)) {
                binding.edtPassword.error = "Invalid password"
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }


        })
    }
}
