package com.example.examplecodekotlin.model

import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadUrl(private var completeListener: CompleteListener) {

    // network call, so run it in another thread
    fun makeNetworkCall(url: String) {

        val thread = Thread(Runnable {

            val data: String = downloadData(url)

            completeListener.downloadComplete(data)
        })

        thread.start()
    }

    @Throws(IOException::class)
    private fun downloadData(url: String): String {

        var inputStream: InputStream? = null

        try {

            val path = URL(url)
            val conn = path.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connect()

            inputStream = conn.inputStream

            return inputStream.bufferedReader().use {

                it.readText()
            }

        } finally {
            inputStream?.close()
        }
    }
}