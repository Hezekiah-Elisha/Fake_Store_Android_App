package com.olefaent.fakestore.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.olefaent.fakestore.FakeStoreApplication
import com.olefaent.fakestore.data.ProductRepository
import com.olefaent.fakestore.model.Product
import kotlinx.coroutines.launch
import retrofit2.HttpException


sealed interface FakeStoreUiState {
    data class Success(val products: List<Product>) : FakeStoreUiState
    object Error : FakeStoreUiState
    object Loading : FakeStoreUiState
}

class FakeStoreViewModel(private val fakeStoreRepository: ProductRepository) : ViewModel(){
    var uiState: FakeStoreUiState by mutableStateOf(FakeStoreUiState.Loading)
        private set

    init {
        getProducts()
    }

    fun getProducts(){
        viewModelScope.launch {
            uiState = FakeStoreUiState.Loading
            try {
                uiState = FakeStoreUiState.Success(
                    fakeStoreRepository.getProducts()

                )
                Log.d("fakestoreapp", "getProducts: ${uiState}")
            } catch (e: Exception) {
                uiState = FakeStoreUiState.Error
                Log.d("fakestoreapp", "getProducts exe: ${e.message}")
            } catch (e: HttpException) {
                uiState  = FakeStoreUiState.Error
                Log.d("fakestoreapp", "getProducts http: ${e.message}")
            }
        }

//         Log.d("fakestoreapp", "getProducts: ${uiState}")
    }

    /**
     * Factory for constructing [FakeStoreViewModel]
     */

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FakeStoreApplication)
                /**
                * The initializer block is called immediately after the ViewModel is created, and
                 * before it is returned to the caller. This is where you can setup any initial
                 * values or state that you want the ViewModel to have.
                 */
                val repository = application.container.productRepository
                FakeStoreViewModel(fakeStoreRepository = repository)
            }
        }
    }

}

