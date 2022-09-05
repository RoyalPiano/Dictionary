package ru.example.dictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ru.example.dictionary.feature_dictionary.presentation.dictionary.DictionaryScreen
import ru.example.dictionary.ui.theme.DictionaryTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryTheme {
                DictionaryScreen()
            }
        }
    }
}