package com.sma.liveler.ui.registration

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sma.liveler.R
import com.sma.liveler.databinding.ActivityRegisterBinding
import com.sma.liveler.repository.UserRepository
import com.sma.liveler.ui.home.HomeActivity
import com.sma.liveler.utils.Utils
import com.sma.liveler.vo.User
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: RegisterViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return RegisterViewModel(
                    this@RegisterActivity,
                    UserRepository(this@RegisterActivity)
                ) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.viewModel = viewModel

        viewModel.success.observe(this, Observer { success ->
            if (success) {
                registrationSuccess()
            } else Utils.alert(this, getString(R.string.error_registration))
        })

        viewModel.loadingVisibility.observe(this, Observer {
            binding.layoutLoading.visibility = it
        })
        viewModel.errorFirstName.observe(this, Observer {
            binding.edtFirstName.error = it
        })
        viewModel.errorPassword.observe(this, Observer {
            binding.edtPassword.error = it
        })
        viewModel.errorEmail.observe(this, Observer {
            binding.edtEmail.error = it
        })
        viewModel.errorPhone.observe(this, Observer {
            binding.edtBirthday.error = it
        })
        viewModel.errorMessage.observe(this, Observer {
            Utils.alert(this, it)
        })

        binding.btnRegister.setOnClickListener {
            val user = User(
                edtFirstName.text.toString(), "", "", edtEmail.text.toString(), "",
                edtPassword.text.toString(), edtPassword.text.toString(),
                edtBirthday.text.toString()
            )
            viewModel.register(user)
        }
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
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            .create()

        if (!dialog.isShowing)
            dialog.show()
    }
}
