package com.app.acronyms.data

import com.app.acronyms.Mocks
import com.app.acronyms.common.Constant
import com.app.acronyms.common.ResultOf
import com.app.acronyms.data.network.APIInterface
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


internal class AppRepositoryImplTest {

    lateinit var mockApiInterface: APIInterface

    @Before
    fun setUp() {
        mockApiInterface = mockk()
    }

    @Test
    fun `verify fetchMeanings returns correct values when API returns with valid result`() {

        //given
        coEvery { mockApiInterface.fetch(any()) } returns Mocks.getMockApiInterfaceResponse()
        val appRepositoryImpl = AppRepositoryImpl(mockApiInterface)

        runBlocking {
            //when
            val fetchMeanings = appRepositoryImpl.fetchMeanings(Mocks.MOCK_QUERY)

            //verify
            assert(fetchMeanings is ResultOf.Success)
            val result = fetchMeanings as ResultOf.Success
            val apiResponseList = result.value
            Assert.assertEquals(apiResponseList!!.size, Mocks.getMockAcronymAPIResults()!!.size)
            Assert.assertEquals(
                apiResponseList.first().lfs?.size,
                Mocks.getMockAcronymAPIResults()!!.first().lfs!!.size
            )
        }
    }

    @Test
    fun `verify fetchMeanings Result of Error when API call fails`() {
        //given
        coEvery { mockApiInterface.fetch(any()) } returns Mocks.getMockApiInterfaceErrorResponse()
        val appRepositoryImpl = AppRepositoryImpl(mockApiInterface)

        runBlocking {
            //when
            val fetchMeanings = appRepositoryImpl.fetchMeanings(Mocks.MOCK_QUERY)

            //verify
            assert(fetchMeanings is ResultOf.Failure)
            val result = fetchMeanings as ResultOf.Failure

            Assert.assertEquals(result.message, Mocks.MOCK_API_ERROR_MESSAGE)
            Assert.assertNotNull(result.throwable)
            Assert.assertEquals(result.throwable!!.message, Mocks.MOCK_API_ERROR_MESSAGE)

        }
    }

    @Test
    fun `verify fetchMeanings handles Generic exceptions`() {
        //given
        coEvery { mockApiInterface.fetch(any()) } throws java.lang.RuntimeException(Constant.ErrorConstant.GENERIC_ERROR_MESSAGE)
        val appRepositoryImpl = AppRepositoryImpl(mockApiInterface)

        runBlocking {
            //when
            val fetchMeanings = appRepositoryImpl.fetchMeanings(Mocks.MOCK_QUERY)

            //verify
            assert(fetchMeanings is ResultOf.Failure)
            val result = fetchMeanings as ResultOf.Failure

            Assert.assertEquals(result.message, Constant.ErrorConstant.GENERIC_ERROR_MESSAGE)
            Assert.assertNotNull(result.throwable)
            Assert.assertEquals(result.throwable!!.message, Constant.ErrorConstant.GENERIC_ERROR_MESSAGE)

        }
    }


    @After
    fun tearDown() {
        unmockkAll()
    }
}