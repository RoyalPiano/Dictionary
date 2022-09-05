package ru.example.dictionary.feature_dictionary.presentation.dictionary

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import ru.example.dictionary.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.example.dictionary.core.util.Resource
import ru.example.dictionary.feature_dictionary.presentation.WordInfoItem
import ru.example.dictionary.feature_dictionary.presentation.WordInfoViewModel

@Composable
fun DictionaryScreen(
    viewModel: WordInfoViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val wordInfos = viewModel.wordInfos.collectAsState().value
    val searchQuery = viewModel.searchQuery.collectAsState().value
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
                    value = searchQuery,
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
                    items(wordInfos.size) { i ->
                        val wordInfo = wordInfos[i]
                        if(i > 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        WordInfoItem(wordInfo = wordInfo)
                        if(i < wordInfos.size - 1) {
                            Divider()
                        }
                    }
                }
            }
            StateHandler(state, Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun <T> StateHandler(state: Resource<T>, modifier: Modifier = Modifier) {
    when(state) {
        is Resource.Loading -> {
            CircularProgressIndicator(modifier)
        }
        is Resource.Error -> {
            Toast.makeText(
                LocalContext.current,
                state.message.asString(),
                Toast.LENGTH_SHORT
            ).show()
        }
        is Resource.Success -> {}
    }
}
