package th.co.cdg.foregroundserviceandroid.data.remote.streaming

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.AsyncTask
import android.util.Log
import android.util.Pair
import android.widget.TextView
import okhttp3.ResponseBody
import th.co.cdg.foregroundserviceandroid.R
import java.io.*

class DownloadAsyncTask(
    private var waitingDialog: Dialog?,
    private val filePart: File,
    private val fileName: String,
    private val callback: (result: Boolean?) -> Unit,
    private val dismissDialog: () -> Unit
) :
    AsyncTask<ResponseBody?, Pair<Int?, Long?>?, String?>() {
//    private val textView = this.waitingDialog?.findViewById<TextView>(R.id.progress)

    private fun doProgress(progressDetails: Pair<Int?, Long?>?) {
        publishProgress(progressDetails)
    }


    override fun doInBackground(vararg params: ResponseBody?): String? {
        saveToDisk(params[0]!!, filePart, fileName)
        return null
    }

    @SuppressLint("SetTextI18n")
    override fun onProgressUpdate(vararg values: Pair<Int?, Long?>?) {
        if (values[0]?.first!! == 100 && values[0]?.second!! == 100L) {
            if (this.waitingDialog != null) {
                this.waitingDialog!!.dismiss()
                this.waitingDialog = null
            }
            this.dismissDialog.invoke()
            this.callback.invoke(true)
        } else {
//            textView?.text =
//                "" + (values[0]?.first!!.toFloat() / values[0]?.second!!.toFloat() * 100) + " %"
        }
    }

    private fun saveToDisk(body: ResponseBody, filePart: File, fileName: String) {
        try {
            if (!filePart.exists()) {
                filePart.mkdir()
            }
            val destinationFile = File("$filePart/$fileName")

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                inputStream = body.byteStream()
                outputStream = FileOutputStream(destinationFile)
                val data = ByteArray(4096)
                var count: Int
                var progress = 0
                val fileSize = body.contentLength()
                Log.d("Log Message", "File Size=$fileSize")
                while (inputStream.read(data).also { count = it } != -1) {
                    outputStream.write(data, 0, count)
                    progress += count
                    val pairs =
                        Pair(progress, fileSize)
                    this.doProgress(pairs)
                    Log.d(
                        "Log Message",
                        "Progress: " + progress + "/" + fileSize + " >>>> " + progress.toFloat() / fileSize * 100

                    )


                }
                outputStream.flush()
                Log.d("Log Message", destinationFile.parent)
                val pairs: Pair<Int?, Long?>? =
                    Pair(100, 100L)
                this.doProgress(pairs)
                return
            } catch (e: IOException) {
                e.printStackTrace()
                val pairs: Pair<Int?, Long?>? =
                    Pair(-1, java.lang.Long.valueOf(-1))
                this.doProgress(pairs)
                Log.d("Log Message", "Failed to save the file!")
                return
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("Log Message", "Failed to save the file!")
            return
        }
    }
}