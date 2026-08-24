package com.example.product_catalog_android.products

import app.cash.turbine.test
import com.example.data.model.Product
import com.example.data.model.Review
import com.example.data.repository.ProductRepository
import com.example.data.result.DataError
import com.example.data.result.DataResult
import com.example.product_catalog_android.MainDispatcherRule
import com.example.product_catalog_android.ui.products.ProductsUiState
import com.example.product_catalog_android.ui.products.ProductsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: ProductRepository
    private lateinit var viewModel: ProductsViewModel

    @Before
    fun setUp() {
        repository = mockk()
    }

    private fun buildProduct(id: Int = 1) = Product(
        id = id,
        title = "Product $id",
        price = 19.99,
        stock = 10,
        description = "A test product",
        category = "test-category",
        images = listOf("https://example.com/image.png"),
        rating = 4.5,
        reviews = listOf(Review(rating = 5, reviewerName = "Jane Doe"))
    )

    @Test
    fun `loadProducts emits Loading then Success when repository returns products`() = runTest {
        val products = listOf(buildProduct(1), buildProduct(2))
        coEvery { repository.getProducts() } returns DataResult.Success(products)

        viewModel = ProductsViewModel(repository)

        viewModel.uiState.test {
            assertEquals(ProductsUiState.Loading, awaitItem())
            assertEquals(ProductsUiState.Success(products), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadProducts emits Empty when repository returns an empty list`() = runTest {

        coEvery { repository.getProducts() } returns DataResult.Success(emptyList())

        viewModel = ProductsViewModel(repository)

        viewModel.uiState.test {
            assertEquals(ProductsUiState.Loading, awaitItem())
            assertEquals(ProductsUiState.Empty, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadProducts emits Empty-mapped error when repository returns EmptyResponse error`() =
        runTest {
            coEvery { repository.getProducts() } returns DataResult.Error(DataError.EmptyResponse)

            viewModel = ProductsViewModel(repository)

            viewModel.uiState.test {
                assertEquals(ProductsUiState.Loading, awaitItem())
                val state = awaitItem()
                assertEquals(
                    ProductsUiState.Error("No products are available right now."),
                    state
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `loadProducts maps NoInternet error to correct message`() = runTest {
        coEvery { repository.getProducts() } returns DataResult.Error(DataError.NoInternet)

        viewModel = ProductsViewModel(repository)

        viewModel.uiState.test {
            assertEquals(ProductsUiState.Loading, awaitItem())
            assertEquals(
                ProductsUiState.Error("No internet connection. Please check your connection."),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadProducts maps ServerError to correct message`() = runTest {
        coEvery { repository.getProducts() } returns DataResult.Error(DataError.ServerError(code = 500))

        viewModel = ProductsViewModel(repository)

        viewModel.uiState.test {
            assertEquals(ProductsUiState.Loading, awaitItem())
            assertEquals(
                ProductsUiState.Error("The server is currently unavailable. Please try again later."),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadProducts maps SerializationError to correct message`() = runTest {
        coEvery { repository.getProducts() } returns DataResult.Error(DataError.SerializationError)

        viewModel = ProductsViewModel(repository)

        viewModel.uiState.test {
            assertEquals(ProductsUiState.Loading, awaitItem())
            assertEquals(
                ProductsUiState.Error("We couldn't read the product information."),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadProducts maps Unknown error to correct message`() = runTest {
        coEvery { repository.getProducts() } returns DataResult.Error(DataError.Unknown)

        viewModel = ProductsViewModel(repository)

        viewModel.uiState.test {
            assertEquals(ProductsUiState.Loading, awaitItem())
            assertEquals(
                ProductsUiState.Error("Something went wrong. Please try again."),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `refreshProducts re-fetches and updates state`() = runTest {
        val initialProducts = listOf(buildProduct(1))
        val refreshedProducts = listOf(buildProduct(1), buildProduct(2))

        coEvery { repository.getProducts() } returns DataResult.Success(initialProducts)
        viewModel = ProductsViewModel(repository)

        viewModel.uiState.test {
            assertEquals(ProductsUiState.Loading, awaitItem())
            assertEquals(ProductsUiState.Success(initialProducts), awaitItem())

            coEvery { repository.getProducts() } returns DataResult.Success(refreshedProducts)
            viewModel.refreshProducts()

            assertEquals(ProductsUiState.Loading, awaitItem())
            assertEquals(ProductsUiState.Success(refreshedProducts), awaitItem())

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 2) { repository.getProducts() }
    }
}