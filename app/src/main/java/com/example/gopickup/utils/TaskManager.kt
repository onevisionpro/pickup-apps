package com.example.gopickup.utils

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class TaskManager(val context: Context?, val mProgressDialog: ProgressDialog?) : AsyncTask<String, Int, String>() {

    @SuppressLint("StaticFieldLeak")
    private lateinit var mWakeLock: PowerManager.WakeLock

    override fun doInBackground(vararg sUrl: String?): String? {
        var input: InputStream? = null
        var output: OutputStream? = null
        var connection: HttpURLConnection? = null
        try {
            val url = URL(sUrl[0])
            connection = url.openConnection() as HttpURLConnection
            connection.connect()

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection!!.responseCode != HttpURLConnection.HTTP_OK) {
                return ("Server returned HTTP " + connection.responseCode
                        + " " + connection.responseMessage)
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            val fileLength = connection.contentLength

            // download the file
            input = connection.inputStream
            output = FileOutputStream("SDCARD")
            val data = ByteArray(4096)
            var total: Long = 0
            var count: Int
            while (input.read(data).also { count = it } != -1) {
                // allow canceling with back button
                if (isCancelled) {
                    input.close()
                    return null
                }
                total += count.toLong()
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((total * 100 / fileLength).toInt())
                output.write(data, 0, count)
            }
        } catch (e: Exception) {
            return e.toString()
        } finally {
            try {
                output?.close()
                input?.close()
            } catch (ignored: IOException) {
            }
            connection?.disconnect()
        }
        return null
    }

    @SuppressLint("WakelockTimeout")
    override fun onPreExecute() {
        super.onPreExecute()
        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
        val pm = context!!.getSystemService(Context.POWER_SERVICE) as PowerManager
        mWakeLock = pm.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            javaClass.name
        )
        mWakeLock.acquire()
        mProgressDialog!!.show()
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        // if we get here, length is known, now set indeterminate to false
        mProgressDialog!!.isIndeterminate = false
        mProgressDialog!!.max = 100
        mProgressDialog!!.progress = values[0]!!
    }

    override fun onPostExecute(s: String?) {
        super.onPostExecute(s)
        mWakeLock!!.release()
        mProgressDialog!!.dismiss()
        if (s != null){
            Toast.makeText(context, "Download error: $s", Toast.LENGTH_LONG).show()
            Log.d("TAG", "onPostExecute: ERROR : $s")
        } else {
            Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show()
            Log.d("TAG", "onPostExecute: File downloaded : $s")
        }
    }
}