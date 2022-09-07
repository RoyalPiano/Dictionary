package ru.example.dictionary.feature_dictionary.presentation.dictionary.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import ru.example.dictionary.feature_dictionary.presentation.interfaces.ScreenState

@Composable
fun <T> ScreenStateHandler(state: ScreenState<T>, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxHeight()) {
        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        state.errorMessage?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
            ) {
                Text(
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption,
                    text = it.asString(),
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}