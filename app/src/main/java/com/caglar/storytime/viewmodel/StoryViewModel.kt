package com.caglar.storytime.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class StoryViewModel() : ViewModel() {

    // API anahtarını burada doğrudan tanımlıyoruz
    private val apiKey = "AIzaSyBTzASkBxP5kg8zDxaFaTxEU-QcttB2pxg"

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey
    )

    val story = MutableLiveData<String>()

    fun generateStory(category: String, characters: List<String>) {
        viewModelScope.launch {
            try {
                // API çağrısını GenerativeModel ile yapabilirsiniz
                val prompt = " ${category} katagorisinde bir hikaye yaz. Karakterler: ${characters.joinToString(", ")}"
                val response = generativeModel.generateContent(prompt)

                Log.d("StoryViewModel", "Story Response: ${response.text}")
                story.value = response.text
            } catch (e: Exception) {
                Log.e("StoryViewModel", "Error generating story", e)
                story.value = "Hikaye oluşturulamadı."
            }
        }
    }
}