package com.sma.liveler.utils

import android.content.Context
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.sma.liveler.R


/**
 * Created by jijith on 10/09/19.
 */
class Utils {

    companion object {

        fun savePreferences(context: Context, key: String, value: String) {
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context) ?: return
            with(sharedPref.edit()) {
                putString(key, value)
                apply()
            }
        }

        fun savePreferences(context: Context, key: String, value: Int) {
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context) ?: return
            with(sharedPref.edit()) {
                putInt(key, value)
                apply()
            }

        }

        fun savePreferences(context: Context, key: String, value: Boolean) {
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context) ?: return
            with(sharedPref.edit()) {
                putBoolean(key, value)
                apply()
            }
        }

        fun loadPreferenceString(context: Context, key: String): String {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            var value = ""
            value = sp.getString(key, value)!!
            return value
        }

        fun loadPreferenceBoolean(context: Context, key: String): Boolean {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            var value = false
            value = sp.getBoolean(key, value)
            return value
        }

        fun loadPreferenceInt(context: Context, key: String): Int {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            var value = -1
            value = sp.getInt(key, value)
            return value
        }

        fun clearPreference(context: Context, key: String) {
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context) ?: return
            with(sharedPref.edit()) {
                remove(key)
                apply()
            }
        }

        fun clearAllPreference(context: Context) {
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context) ?: return
            with(sharedPref.edit()) {
                clear()
                apply()
            }
        }

        fun logout(context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
//            editor.remove(Constants.TOKEN)
//            editor.remove(Constants.USERID)
//            editor.remove(Constants.EMAIL)
//            editor.remove(Constants.PRO_PIC)
//            editor.remove(Constants.USER_NAME)
            //        editor.clear();
            editor.apply()
        }

        /**
         * Method to check whether the network is connected or not.
         */
        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            val activeNetwork = cm?.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }

        fun alert(context: Context, msg: String) {
            val dialog = AlertDialog.Builder(
                context,
                R.style.Theme_App_Light_Dialog_NoActionBar
            )
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.btn_ok)) { dialogInterface, i ->
                    dialogInterface.cancel()
                    dialogInterface.dismiss()
                }
                .create()

            if (!dialog.isShowing)
                dialog.show()
        }

        fun toast(context: Context, msg: String) {
            android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_LONG).show()
        }

        fun showSnackBar(view: View, msg: String) {
            Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null)
                .setDuration(6000)
                .show()
        }

        fun hideKeyboard(context: Context, view: View) {
            val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}