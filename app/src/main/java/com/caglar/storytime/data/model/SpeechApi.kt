package com.caglar.storytime.data.model

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun textToSpeech(text: String, outputFilePath: String, onCompletion: () -> Unit) {
    val client = OkHttpClient()


    val headers = Headers.Builder()
        .add("Accept", "application/json")
        .add("xi-api-key", apiKey)
        .build()

    val escapedText = text
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\b", "\\b")
        .replace("\u000c", "\\f")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")

    val requestBody = RequestBody.create(
        "application/json; charset=utf-8".toMediaType(),
        """
        {
            "text": "$escapedText",
            "model_id": "eleven_multilingual_v2",
            "voice_settings": {
                "stability": 0.5,
                "similarity_boost": 0.8,
                "style": 0.0,
                "use_speaker_boost": true
            }
        }
        """.trimIndent()
    )

    val request = Request.Builder()
        .url(url)
        .post(requestBody)
        .headers(headers)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            onCompletion()
        }

        override fun onResponse(call: Call, response: Response) {
            if (!response.isSuccessful) {
                println("Failed with code: ${response.code}")
                val errorBody = response.body?.string()
                println("Response body: $errorBody")
                onCompletion()
                throw IOException("Unexpected code $response")
            }

            val audioBytes = response.body?.bytes() ?: return
            val file = File(outputFilePath)
            FileOutputStream(file).use { fos ->
                fos.write(audioBytes)
            }

            println("Audio stream saved successfully at $outputFilePath")
            onCompletion()
        }
    })
}