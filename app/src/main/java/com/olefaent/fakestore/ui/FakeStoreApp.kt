package com.olefaent.fakestore.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.olefaent.fakestore.ui.screens.FakeStoreViewModel
import com.olefaent.fakestore.ui.screens.HomeScreen
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FakeStoreApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = { StoreTopAppBar(scrollBehavior = scrollBehavior) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val fakeStoreViewModel: FakeStoreViewModel = viewModel(factory = FakeStoreViewModel.Factory)

            HomeScreen(
                uiState = fakeStoreViewModel.uiState,
                retryAction = fakeStoreViewModel::getProducts
            )
            Log.d("fakestoreapp", "FakeStoreApp: ${fakeStoreViewModel.uiState}")

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
                title = {
            Text(
                text = "Fake Store",
                style = MaterialTheme.typography.headlineSmall,
            )
        },
    )
}
