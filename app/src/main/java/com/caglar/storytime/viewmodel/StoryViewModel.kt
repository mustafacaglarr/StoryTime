package com.caglar.storytime.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class StoryViewModel() : ViewModel() {

    val selectedCategories = mutableStateListOf<String>()
    val selectedCartoonCategories = mutableStateListOf<String>()
    val selectedHeroCategories = mutableStateListOf<String>()




    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey
    )

    val story = MutableLiveData<String>()

    fun generateStory() {
        viewModelScope.launch {
            try {
                // Tüm kategoriler ve karakterleri birleştiriyoruz
                val category = selectedCategories.joinToString(", ")
                val characters = selectedHeroCategories + selectedCartoonCategories

                // API çağrısını GenerativeModel ile yapıyoruz
                val prompt = "Bir çocuk hikayesi yaz . Kategoriler: ${category}. Karakterler: ${characters.joinToString(", ")}"
                val response = generativeModel.generateContent(prompt)

                Log.d("StoryViewModel", "Story Response: ${response.text}")
                story.value = response.text
            } catch (e: Exception) {
                Log.e("StoryViewModel", "Error generating story", e)
                story.value = "Hikaye oluşturulamadı."
            }
        }
    }

    fun addCategory(listType: ListType, category: String) {
        when (listType) {
            ListType.GENERAL -> if (!selectedCategories.contains(category)) selectedCategories.add(category)
            ListType.CARTOON -> if (!selectedCartoonCategories.contains(category)) selectedCartoonCategories.add(category)
            ListType.HERO -> if (!selectedHeroCategories.contains(category)) selectedHeroCategories.add(category)
        }
    }

    fun removeCategory(listType: ListType, category: String) {
        when (listType) {
            ListType.GENERAL -> selectedCategories.remove(category)
            ListType.CARTOON -> selectedCartoonCategories.remove(category)
            ListType.HERO -> selectedHeroCategories.remove(category)
        }
    }
}

enum class ListType {
    GENERAL, CARTOON, HERO
}