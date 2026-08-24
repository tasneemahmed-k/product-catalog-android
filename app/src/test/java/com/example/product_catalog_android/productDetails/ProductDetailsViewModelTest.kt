package com.example.product_catalog_android.productDetails

import app.cash.turbine.test
import com.example.data.model.Product
import com.example.data.model.Review
import com.example.data.repository.ProductRepository
import com.example.data.result.DataError
import com.example.data.result.DataResult
import com.example.product_catalog_android.MainDispatcherRule
import com.example.product_catalog_android.ui.details.ProductDetailsUiState
import com.example.product_catalog_android.ui.details.ProductDetailsViewModel
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
class ProductDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: ProductRepository
    private lateinit var viewModel: ProductDetailsViewModel

    @Before
    fun setUp() {
        repository = mockk()
        viewModel = ProductDetailsViewModel(repository)
    }

    private fun buildProduct(id: Int = 1) = Product(
        id = id,
        title = "Product $id",
        price = 19.99,
        stock = 10,
        description = "A test product",
        category = "test-category",
        images = listOf(
            "https://example.com/image.png"
        ),
        rating = 4.5,
        reviews = listOf(
            Review(
                rating = 5,
                reviewerName = "Jane Doe"
            )
        )
    )

    @Test
    fun `loadProduct with id 0 emits Empty and never calls repository`() = runTest {

        viewModel.uiState.test {

            assertEquals(
                ProductDetailsUiState.Loading,
                expectMostRecentItem()
            )

            viewModel.loadProduct(0)

            assertEquals(
                ProductDetailsUiState.Empty,
                awaitItem()
            )
//      I've seen all the states I care about. Stop testing this Flow and ignore anything else it might emit
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 0) {
            repository.getProductById(any())
        }
    }

    @Test
    fun `loadProduct with negative id emits Empty and never calls repository`() = runTest {

        viewModel.uiState.test {

            assertEquals(
                ProductDetailsUiState.Loading,
                expectMostRecentItem()
            )

            viewModel.loadProduct(-5)

            assertEquals(
                ProductDetailsUiState.Empty,
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 0) {
            repository.getProductById(any())
        }
    }

//    runTest: is to test a coroutine
    @Test
    fun `loadProduct emits Success when repository returns a product`() = runTest {

        val product = buildProduct(42)

        coEvery {
            repository.getProductById(42)
        } returns DataResult.Success(product)

        viewModel.uiState.test {

            assertEquals(
                ProductDetailsUiState.Loading,
                expectMostRecentItem()
            )

            viewModel.loadProduct(42)

            assertEquals(
                ProductDetailsUiState.Success(product),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadProduct maps NoInternet error to correct message`() = runTest {

        coEvery {
            repository.getProductById(42)
        } returns DataResult.Error(DataError.NoInternet)

        viewModel.uiState.test {

            assertEquals(
                ProductDetailsUiState.Loading,
                expectMostRecentItem()
            )

            viewModel.loadProduct(42)

            assertEquals(
                ProductDetailsUiState.Error(
                    "No internet connection. Please check your connection."
                ),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadProduct maps InvalidProduct error to correct message`() = runTest {

        coEvery {
            repository.getProductById(42)
        } returns DataResult.Error(DataError.InvalidProduct)

        viewModel.uiState.test {

            assertEquals(
                ProductDetailsUiState.Loading,
                expectMostRecentItem()
            )

            viewModel.loadProduct(42)

            assertEquals(
                ProductDetailsUiState.Error(
                    "The product information is invalid."
                ),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadProduct maps ServerError to correct message`() = runTest {

        coEvery {
            repository.getProductById(42)
        } returns DataResult.Error(
            DataError.ServerError(code = 503)
        )

        viewModel.uiState.test {

            assertEquals(
                ProductDetailsUiState.Loading,
                expectMostRecentItem()
            )

            viewModel.loadProduct(42)

            assertEquals(
                ProductDetailsUiState.Error(
                    "The server is currently unavailable. Please try again later."
                ),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadProduct maps SerializationError to correct message`() = runTest {

        coEvery {
            repository.getProductById(42)
        } returns DataResult.Error(
            DataError.SerializationError
        )

        viewModel.uiState.test {

            assertEquals(
                ProductDetailsUiState.Loading,
                expectMostRecentItem()
            )

            viewModel.loadProduct(42)

            assertEquals(
                ProductDetailsUiState.Error(
                    "We couldn't read the product information."
                ),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadProduct maps ProductNotFound error to correct message`() = runTest {

        coEvery {
            repository.getProductById(42)
        } returns DataResult.Error(
            DataError.ProductNotFound
        )

        viewModel.uiState.test {

            assertEquals(
                ProductDetailsUiState.Loading,
                expectMostRecentItem()
            )

            viewModel.loadProduct(42)

            assertEquals(
                ProductDetailsUiState.Error(
                    "The product could not be found."
                ),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadProduct maps EmptyResponse error to correct message`() = runTest {

        coEvery {
            repository.getProductById(42)
        } returns DataResult.Error(
            DataError.EmptyResponse
        )

        viewModel.uiState.test {

            assertEquals(
                ProductDetailsUiState.Loading,
                expectMostRecentItem()
            )

            viewModel.loadProduct(42)

            assertEquals(
                ProductDetailsUiState.Error(
                    "No products are available right now."
                ),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadProduct maps Unknown error to correct message`() = runTest {

        coEvery {
            repository.getProductById(42)
        } returns DataResult.Error(
            DataError.Unknown
        )

        viewModel.uiState.test {

            assertEquals(
                ProductDetailsUiState.Loading,
                expectMostRecentItem()
            )

            viewModel.loadProduct(42)

            assertEquals(
                ProductDetailsUiState.Error(
                    "Something went wrong. Please try again."
                ),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadProduct can be called again after an error and recover with Success`() = runTest {

        coEvery {
            repository.getProductById(42)
        } returns DataResult.Error(
            DataError.NoInternet
        )

        viewModel.uiState.test {

            assertEquals(
                ProductDetailsUiState.Loading,
                expectMostRecentItem()
            )

            viewModel.loadProduct(42)

            assertEquals(
                ProductDetailsUiState.Error(
                    "No internet connection. Please check your connection."
                ),
                awaitItem()
            )

            val product = buildProduct(42)

            coEvery {
                repository.getProductById(42)
            } returns DataResult.Success(product)

            viewModel.loadProduct(42)

            assertEquals(
                ProductDetailsUiState.Loading,
                awaitItem()
            )

            assertEquals(
                ProductDetailsUiState.Success(product),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 2) {
            repository.getProductById(42)
        }
    }
}