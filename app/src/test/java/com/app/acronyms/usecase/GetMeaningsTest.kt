package com.app.acronyms.usecase

import com.app.acronyms.Mocks
import com.app.acronyms.Mocks.MOCK_QUERY
import com.app.acronyms.common.ResultOf
import com.app.acronyms.data.AppRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class GetMeaningsTest {

    private lateinit var mockRepository: AppRepository

    @Before
    fun setUp() {
        mockRepository = mockk()//mock network layer
    }

    @Test
    fun `verify empty list is returned when search with an empty sting`() {
        val meaningsUseCase = GetMeanings(mockRepository)
        runBlocking {
            val result = meaningsUseCase.execute("")
            assert(result is ResultOf.Success)
            assert((result as ResultOf.Success).value.isEmpty())
        }

    }

    @Test
    fun `verify empty list is returned when search with an null sting`() {
        val meaningsUseCase = GetMeanings(mockRepository)
        runBlocking {
            val result = meaningsUseCase.execute(null)
            assert(result is ResultOf.Success)
            assert((result as ResultOf.Success).value.isEmpty())
        }
    }

    @Test
    fun `verify when searched with a valid query the response is parsed as expected`() {
        //given
        val acronymResults = Mocks.getMockAcronymAPIResults()
        val expected = Mocks.expectedGetMeaningsUseCaseResults().value
        coEvery { mockRepository.fetchMeanings(MOCK_QUERY) } returns ResultOf.Success(acronymResults)

        runBlocking {
            // when
            val result = GetMeanings(mockRepository).execute(MOCK_QUERY)

            //verify
            assert(result is ResultOf.Success)
            val resultList = result as ResultOf.Success
            val actual = result.value
            assert(result.value.isNotEmpty())
            assertEquals(resultList.value.size, expected.size)
            assertTrue(actual.containsAll(expected))
        }

    }

    @Test
    fun `verify GetMeanings returns empty result when api response does not have result field`() {
        //given
        val acronymResults = Mocks.getMockMissingAcronymAPIResults()
        val expected = emptyList<String>()
        coEvery { mockRepository.fetchMeanings(MOCK_QUERY) } returns ResultOf.Success(acronymResults)

        runBlocking {
            // when
            val result = GetMeanings(mockRepository).execute(MOCK_QUERY)

            //verify
            assert(result is ResultOf.Success)
            val resultList = result as ResultOf.Success
            val actual = resultList.value
            assert(actual.isEmpty())
            assertEquals(actual, expected)

        }

    }


    @After
    fun tearDown() {
        unmockkAll()
    }


}