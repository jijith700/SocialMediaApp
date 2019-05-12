package com.sma.socialmediaapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Environment
import android.preference.PreferenceManager
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sma.socialmediaapp.R
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by jijith on 12/26/17.
 */
class Utils {

    companion object {

        val TAG = Utils::class.java.simpleName

        fun savePreferences(context: Context, key: String, value: String) {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sp.edit()
            editor.putString(key, value)
            editor.apply()
            //Log.w("Preference saved  .", value);

        }

        fun savePreferences(context: Context, key: String, value: Int) {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sp.edit()
            editor.putInt(key, value)
            editor.apply()
            //Log.w("Preference saved  .", value);

        }

        fun savePreferences(context: Context, key: String, value: Boolean) {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)

            val editor = sp.edit()
            editor.putBoolean(key, value)
            editor.apply()
            //Log.w("Preference saved  .", value);

        }

        fun loadPreferences(context: Context, key: String): String {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            var value = ""
            value = sp.getString(key, "")
            return value
        }

        fun loadPreferencesBoolean(context: Context, key: String): Boolean {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            var value = false
            value = sp.getBoolean(key, false)
            return value
        }

        fun loadPreferencesInt(context: Context, key: String): Int {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            var value = -1
            value = sp.getInt(key, -1)
            return value
        }

        fun clearPreference(context: Context, key: String) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.remove(key).apply()
        }

        fun clearAllPreference(context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.clear()
            editor.apply()
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

        fun isNetworkConnected(context: Context): Boolean {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (manager.activeNetworkInfo != null) {
                if (manager.activeNetworkInfo.isAvailable && manager.activeNetworkInfo.isConnected)
                    true
                else
                    false
            } else {
                false
            }
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

            if (dialog != null && !dialog.isShowing)
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

        fun getDatabasePath(context: Context) {
            AppLog.d(TAG, context.getDatabasePath(context.getString(R.string.app_name)).absolutePath)
        }

        @Throws(IOException::class)
        fun copyAppDbToDownloadFolder(context: Context, address: String) {
            val filePath =
                Environment.getExternalStorageDirectory().absolutePath + File.separator + context.getString(R.string.app_name)

            val backupDB = File(filePath, context.getString(R.string.app_name) + ".db")
            val currentDB = context.getDatabasePath(context.getString(R.string.app_name))
            if (currentDB.exists()) {
                val src = FileInputStream(currentDB).channel
                val dst = FileOutputStream(backupDB).channel
                dst.transferFrom(src, 0, src.size())
                src.close()
                dst.close()
            }
        }

        fun getLogoName(context: Context): Spannable {
            val spannable = SpannableString(context.getString(R.string.app_name))
            spannable.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorAccent)),
                0,
                1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            AppLog.d(TAG, spannable.toString())
            return spannable
        }
    }
}