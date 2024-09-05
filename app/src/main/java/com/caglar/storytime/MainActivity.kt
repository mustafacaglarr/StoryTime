package com.caglar.storytime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.caglar.storytime.data.model.getVoices
import com.caglar.storytime.data.model.textToSpeech
import com.caglar.storytime.ui.navigation.MyAppNavHost
import com.caglar.storytime.ui.screens.category.CategoryScreen
import com.caglar.storytime.ui.screens.category.ChooseScreen
import com.caglar.storytime.ui.screens.character.CartoonScreen
import com.caglar.storytime.ui.screens.character.HeroScreen
import com.caglar.storytime.ui.screens.splash.SplashScreen
import com.caglar.storytime.ui.screens.story.StoryScreen
import com.caglar.storytime.ui.screens.story.VoiceScreen
import com.caglar.storytime.ui.theme.StoryTimeTheme
import com.caglar.storytime.viewmodel.StoryViewModel


class MainActivity : ComponentActivity() {
    private val storyViewModel: StoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StoryTimeTheme {

                val storyViewModel = StoryViewModel()
                MyAppNavHost(storyViewModel = storyViewModel)



            }
        }
    }
}


