package com.example.storyapp.UI.Home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.storyapp.Adapter.StoryAdapter
import com.example.storyapp.Data.StoryRepository
import com.example.storyapp.Helper.UserPreference
import com.example.storyapp.Network.ListStoryResult
import com.example.storyapp.Utils.CoroutinesTestRule
import com.example.storyapp.Utils.DataDummy
import com.example.storyapp.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class HomeHomeViewModelTest {
    private lateinit var homeViewModel: HomeViewModel
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private val storyRepository = Mockito.mock(StoryRepository::class.java)
    private var preference = Mockito.mock(UserPreference::class.java)
    private val dummyStory = DataDummy.generateDummyStory()

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(preference, storyRepository)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `when get List Story is Successful`() = runTest {
        val data: PagingData<ListStoryResult> = PagingSourceTest.snapshot(dummyStory)
        val expectedStory = MutableLiveData<PagingData<ListStoryResult>>()
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVVsSkdZMkI2b3VRZmY1UDciLCJpYXQiOjE2ODMzNDE1NTJ9.ac7Y_k3Z2BztrEncTKIHNzqdB8nFJZgOTppd1aMhcrw"
        expectedStory.value = data
        `when`(storyRepository.getStory("Bearer $token")).thenReturn(expectedStory)

        val actualStory: PagingData<ListStoryResult> =
            homeViewModel.getStory(token).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)

        Mockito.verify(storyRepository).getStory("Bearer $token")
        assertNotNull(differ.snapshot())
        assertEquals(dummyStory, differ.snapshot())
        assertEquals(dummyStory.size, differ.snapshot().size)
        assertEquals(dummyStory[0], differ.snapshot()[0])
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `when List Story Null`() = runTest {
        val expectedStory = MutableLiveData<PagingData<ListStoryResult>>()
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVVsSkdZMkI2b3VRZmY1UDciLCJpYXQiOjE2ODMzNDE1NTJ9.ac7Y_k3Z2BztrEncTKIHNzqdB8nFJZgOTppd1aMhcrw"
        expectedStory.value = PagingData.empty()

        `when`(storyRepository.getStory("Bearer $token")).thenReturn(expectedStory)

        val actualStory: PagingData<ListStoryResult> = homeViewModel.getStory(token).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)

        Mockito.verify(storyRepository).getStory("Bearer $token")
        assertNotNull(differ.snapshot())
        assertTrue(differ.snapshot().isEmpty())
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}

class PagingSourceTest: PagingSource<Int, LiveData<List<ListStoryResult>>>() {
    companion object {
        fun snapshot(items: List<ListStoryResult>): PagingData<ListStoryResult> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStoryResult>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStoryResult>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}