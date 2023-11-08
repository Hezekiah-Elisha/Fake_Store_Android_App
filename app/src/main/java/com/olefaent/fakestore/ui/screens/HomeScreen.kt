package com.olefaent.fakestore.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.olefaent.fakestore.R
import com.olefaent.fakestore.model.Product

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,
    uiState: FakeStoreUiState
) {
    when (uiState) {
        is FakeStoreUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxWidth())
        is FakeStoreUiState.Success -> ProductsGridScreen(uiState.products, modifier)
        is FakeStoreUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxWidth())
    }
}

@Composable
fun ProductsGridScreen(
    products: List<Product>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        Log.d("Products", "ProductsGridScreen: ${products}")
        items(products.size) { index ->
            ProductCard(products[index])
        }
    }
}


@Composable
fun ProductCard(product: Product, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.image)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.product_photo),
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxWidth()
                .height(200.dp),
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
        )
        Text(text = product.title)
        Text(text = product.price.toString())
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Add to cart")
        }
    }
}



@Composable
fun LoadingScreen() {
    Text(text ="Loading...")
}

@Composable
fun ErrorScreen(retryAction: () -> Unit , modifier: Modifier = Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = ""
        )
        Text(text = "Error loading products")
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}


@Preview(
    showBackground = true
)
@Composable
fun LoadingScreenPreview() {
    ErrorScreen(retryAction = { /*TODO*/ })
}


@Preview(
    showBackground = true,
    name = "Light Mode"
)
@Composable
fun HomeScreenPreview() {
//    HomeScreen({})
}

