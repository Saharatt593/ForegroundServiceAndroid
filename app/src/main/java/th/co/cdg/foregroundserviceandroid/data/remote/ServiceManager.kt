package th.co.cdg.foregroundserviceandroid.data.remote

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import th.co.cdg.foregroundserviceandroid.Application
import th.co.cdg.foregroundserviceandroid.R
import th.co.cdg.foregroundserviceandroid.data.remote.entity.ApiResponse
import th.co.cdg.foregroundserviceandroid.data.remote.streaming.DownloadAsyncTask
import th.co.cdg.foregroundserviceandroid.util.Constants
import th.co.cdg.foregroundserviceandroid.util.UtilsUI
import java.io.File

object ServiceManager {
    private val gson = Gson()

    private lateinit var downloadAsyncTask: DownloadAsyncTask

    private var waitingDialog: Dialog? = null
    fun <T> service(
        showDialog: Boolean,
        closeLoading: Boolean,
        noContentDialog: Boolean,
        isShowErrorDialog: Boolean? = true,
        call: Call<T>,
        isStream: Boolean? = false,
        callbackStream: (result: Boolean?) -> Unit = {},
        callback: (result: T?) -> Unit = {}
    ) {
        // check internet
        if (!UtilsUI.isNetworkAvailable()) {
            UtilsUI.messageDialogUtil(
                null,
                Application.mApplicationContext?.getString(R.string.NOCONTENT_NETWORK)
            )
        } else {
            if (showDialog) {
                showWaitingDialog(isStream!!)
            }
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.code() == Constants.HTTP_UNAUTHORIZED || response.code() == Constants.HTTP_FORBIDDEN) run {

                        val result: ApiResponse<*>? = try {
                            gson.fromJson(
                                (response.errorBody()!!.string()),
                                ApiResponse::class.java
                            )
                        } catch (e: JsonSyntaxException) {
                            null
                        }
                        if (result?.message == null) {
                            // refresh token are expired

                            if (showDialog) {
                                dismissWaitingDialog()
                            }

                            if (call.request().url.toString()
                                    .contains("auth")
                            ) {
                                UtilsUI.messageDialogUtil(
                                    null,
                                    Application.mApplicationContext?.getString(R.string.UNKONW_LOGIN)
                                )
                            } else {

                                UtilsUI.messageDialogUtil(
                                    null,
                                    Application.mApplicationContext?.getString(R.string.AUTHORIZEN_LOGIN)
                                ) {
                                    // todo delete user dao
//                                    UserPreferences.setNull(Application.mApplicationContext)
//                                    // goto login
//                                    val intent = Intent(
//                                        Application.mApplicationContext,
//                                        LoginActivity::class.java
//                                    )

//                                    Application.mApplicationContext!!.startActivity(intent)
//                                    (Application.mApplicationContext as Activity).finishAffinity()
                                }
                            }
                        } else {
                            if (showDialog) {
                                dismissWaitingDialog()
                                UtilsUI.messageDialogUtil(null, result.message) {
                                }

                            }
                        }

                    } else if (response.code() == Constants.HTTP_OK) {
                        if (!isStream!!) {
                            callback(response.body())
                            if (showDialog) {
                                if (closeLoading) {
                                    dismissWaitingDialog()
                                }
                            }
                        } else {
                            downloadAsyncTask =
                                DownloadAsyncTask(
                                    waitingDialog,
                                    File(Application.mApplicationContext!!.filesDir.toString()),
                                    Constants.REPORT,
                                    callbackStream
                                ) {
                                    dismissWaitingDialog()
                                }
                            downloadAsyncTask.execute(response.body() as ResponseBody)
                        }
                    } else if (response.code() == Constants.HTTP_ERROR) {
                        callback(response.body())
                        try {
                            val result = gson.fromJson(
                                response.errorBody()?.string(),
                                ApiResponse::class.java
                            )
                            if (showDialog) {
                                if (closeLoading) {
                                    dismissWaitingDialog()
                                }
                            }
                            if (isShowErrorDialog!!) {
                                if (call.request().url.toString()
                                        .contains("renewToken")
                                ) {
//                                    UserPreferences.setNull(Application.mApplicationContext)
//                                    UtilsUI.messageDialogUtil(
//                                        null,
//                                        Application.mApplicationContext?.getString(R.string.AUTHORIZEN_LOGIN)
//                                    ) {
//                                        val intent = Intent(
//                                            Application.mApplicationContext,
//                                            LoginActivity::class.java
//                                        )
//                                        Application.mApplicationContext!!.startActivity(intent)
//                                        (Application.mApplicationContext as Activity).finishAffinity()
//                                    }

                                } else {
                                    UtilsUI.messageDialogUtil(
                                        null,
                                        if (result != null) {
                                            result.message
                                                ?: Application.mApplicationContext?.getString(R.string.EXCEPTION)
                                        } else {
                                            Application.mApplicationContext?.getString(R.string.EXCEPTION)
                                        }
                                    ) {

                                    }
                                }
                            }

                        } catch (e: Exception) {
                            if (showDialog) {
                                if (closeLoading) {
                                    dismissWaitingDialog()
                                }
                            }
                        }

                    } else if (response.code() == Constants.HTTP_NOCONTENT) {
                        callback(response.body())
                        if (showDialog) {
                            dismissWaitingDialog()
                            if (noContentDialog) {
                                UtilsUI.messageDialogUtil(
                                    null,
                                    Application.mApplicationContext?.getString(R.string.STRING_NOCONTENT)
                                )
                            }
                        }

                    } else if (response.code() == Constants.HTTP_INTERNAL_ERROR) {
                        if (showDialog) {
                            dismissWaitingDialog()
                        }
                        val result: ApiResponse<*>? = try {
                            gson.fromJson(
                                (response.errorBody()!!.string()),
                                ApiResponse::class.java
                            )
                        } catch (e: JsonSyntaxException) {
                            null
                        }
                        if (showDialog) {
                            UtilsUI.messageDialogUtil(
                                null, result?.message
                                    ?: Application.mApplicationContext?.getString(R.string.EXCEPTION_NETWORK_ADMIN)
                            )
                        }

                    } else if (response.code() == Constants.SERVER_DOWN) {
//                        val intent = Intent(
//                            Application.mApplicationContext,
//                            CloseActivity::class.java
//                        )
//                        Application.mApplicationContext!!.startActivity(intent)
//                        (Application.mApplicationContext as Activity).finishAffinity()
                    } else {
                        if (showDialog) {
                            dismissWaitingDialog()
                            UtilsUI.messageDialogUtil(null, getResponseMessage(response))
                        }

                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    if (showDialog) {
                        dismissWaitingDialog()
                        if ("java.net.SocketTimeoutException" == t.toString()
                                .substring(0, t.toString().indexOf(":"))
                        ) {
                            UtilsUI.messageDialogUtil(
                                null,
                                Application.mApplicationContext?.getString(R.string.NOCONTENT_RO_TIME)
                            )
                        } else {
                            UtilsUI.messageDialogUtil(
                                null,
                                Application.mApplicationContext?.getString(R.string.NOCONTENT)
                            )
                        }

                    }
                }
            })
        }
    }

    private fun showWaitingDialog(isStream: Boolean) {
        try {
            if (waitingDialog == null) {
                waitingDialog =   UtilsUI.getWaitingDialog()
//                    UtilsUI.getWaitingDialogStream()
//                } else {
//                    UtilsUI.getWaitingDialog()
//                }
            }
            waitingDialog!!.show()
        } catch (e: RuntimeException) {
        }
    }

    fun dismissWaitingDialog() {
        try {
            if (waitingDialog != null) {
                waitingDialog!!.dismiss()
                waitingDialog = null
            }
        } catch (e: IllegalArgumentException) {
        }
    }

    private fun <T> getResponseMessage(response: Response<T>): String {
        return if (response.errorBody() != null) {
            try {
                // todo error Value Permission of type java.lang.String cannot be converted to JSONObject
                val mJson = JSONObject(response.errorBody()!!.string())
                mJson.get("message").toString()
            } catch (e: Exception) {
                e.printStackTrace()
                Application.mApplicationContext?.getString(R.string.EXCEPTION).toString()
            }
        } else {
            response.message()
        }
    }
}
