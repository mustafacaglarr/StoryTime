package com.caglar.storytime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.caglar.storytime.ui.screens.category.CategoryScreen
import com.caglar.storytime.ui.screens.character.CartoonScreen
import com.caglar.storytime.ui.screens.character.HeroScreen
import com.caglar.storytime.ui.screens.story.StoryScreen
import com.caglar.storytime.ui.theme.StoryTimeTheme
import com.caglar.storytime.viewmodel.StoryViewModel

class MainActivity : ComponentActivity() {
    private val storyViewModel: StoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StoryTimeTheme {
                // Call the StoryScreen composable and pass the ViewModel
                //StoryScreen(viewModel = storyViewModel)
                //CategoryScreen(viewModel = storyViewModel)
                HeroScreen()
            }
        }
    }
}


