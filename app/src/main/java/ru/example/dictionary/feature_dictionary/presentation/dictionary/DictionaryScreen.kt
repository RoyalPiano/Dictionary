package ru.example.dictionary.feature_dictionary.presentation.dictionary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import ru.example.dictionary.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.example.dictionary.feature_dictionary.presentation.dictionary.components.WordInfoItem
import ru.example.dictionary.feature_dictionary.presentation.dictionary.components.ScreenStateHandler

@Composable
fun DictionaryScreen(
    viewModel: WordInfoViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    SwipeRefresh(
        state = rememberSwipeRefreshState(false),
        onRefresh = {
            viewModel.refreshData()
        }) {
        Box(
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                TextField(
                    value = viewModel.searchQuery.collectAsState().value,
                    onValueChange = viewModel::onSearch,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.search_tip)
                        )
                    })
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.data.size) { i ->
                        val wordInfo = state.data[i]
                        if(i > 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        WordInfoItem(wordInfo = wordInfo)
                        if(i < state.data.size - 1) {
                            Divider()
                        }
                    }
                }
            }
            ScreenStateHandler(state, Modifier.align(Alignment.Center))
        }
    }
}
