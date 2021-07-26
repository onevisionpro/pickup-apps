package com.example.gopickup.utils

import android.os.AsyncTask
import android.os.Environment
import java.io.File
import java.io.IOException


open class DownloadFile(val url: String) :
    AsyncTask<String?, Void?, Void?>() {

    override fun doInBackground(vararg params: String?): Void? {
        val fileUrl = url[0] // -> http://maven.apache.org/maven-1.x/maven.pdf

        val fileName = url.substringAfter("pdf/") // -> maven.pdf
        val extStorageDirectory: String =
            Environment.getExternalStorageDirectory().toString()
        val folder = File(extStorageDirectory, "testthreepdf")
        folder.mkdir()
        val pdfFile = File(folder, fileName)
        try {
            pdfFile.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        FileDownloader().downloadFile(fileUrl = fileName, directory = pdfFile)
        return null
    }
}