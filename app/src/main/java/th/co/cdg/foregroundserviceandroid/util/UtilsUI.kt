package th.co.cdg.foregroundserviceandroid.util

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AlertDialog
import th.co.cdg.foregroundserviceandroid.Application
import th.co.cdg.foregroundserviceandroid.R


class UtilsUI {
    companion object UtilsUI {
        fun getWaitingDialog(): Dialog {
            val progressDialog = ProgressDialog(Application.mApplicationContext)
            progressDialog.setMessage(Application.mApplicationContext?.resources?.getString(R.string.loading))
            progressDialog.isIndeterminate = false
            progressDialog.setCancelable(false)
            return progressDialog
        }

        fun messageDialogUtil(title: String?, msg: String?) {
            val builder = AlertDialog.Builder(Application.mApplicationContext!!)
            builder.setTitle(title)
            builder.setMessage(msg)
            builder.setPositiveButton(Application.mApplicationContext!!.resources!!.getString(R.string.positive)) { dialog, _ -> dialog.dismiss() }
            builder.setCancelable(false)
            builder.create()
            builder.show()
        }


        fun messageDialogUtil(title: String?, msg: String?, callback: () -> Unit) {
            val builder = AlertDialog.Builder(Application.mApplicationContext!!)
            builder.setTitle(title)
            builder.setMessage(msg)
            builder.setPositiveButton(Application.mApplicationContext!!.resources!!.getString(R.string.positive)) { dialog, _ ->
                callback.invoke()
                dialog.dismiss()
            }
            builder.setCancelable(false)
            builder.create()
            builder.show()
        }

        fun isNetworkAvailable(): Boolean {
            if (Application.mApplicationContext != null) {
                (Application.mApplicationContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
                    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getNetworkCapabilities(activeNetwork)?.run {
                            when {
                                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                                hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                                hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN) -> true
                                hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                                hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE) -> true
                                else -> false
                            }
                        } ?: false
                    } else {
                        //VERSION.SDK_INT < M
                        val connectivityManager =
                            Application.mApplicationContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
                        return connectivityManager?.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
                    }
                }
            } else {
                return false
            }
        }
    }
}