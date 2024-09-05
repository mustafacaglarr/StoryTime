package com.caglar.storytime.data.model

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

fun getVoices() {
    val client = OkHttpClient()

    // Ses modeli listeleme URL'si


    // API isteği için başlıklar
    val headers = Headers.Builder()
        .add("Accept", "application/json")
        .add("xi-api-key", apiKey)
        .build()

    // API isteği
    val request = Request.Builder()
        .url(url)
        .headers(headers)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            println("başarısız lavuk")
        }

        override fun onResponse(call: Call, response: Response) {
            if (!response.isSuccessful) {
                println("Failed with code: ${response.code}")
                println("Response body: ${response.body?.string()}")
                throw IOException("Unexpected code $response")
            }

            // Yanıtı işleme
            val responseData = response.body?.string() ?: return
            val jsonObject = JSONObject(responseData)
            println("Available voices: $jsonObject")
        }
    })
}