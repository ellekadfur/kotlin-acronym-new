package com.app.acronyms.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.acronyms.R
import com.app.acronyms.common.clear
import com.app.acronyms.common.debounce
import com.app.acronyms.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val searchListAdapter: SearchListAdapter = SearchListAdapter()
    private val viewModel by viewModels<SearchViewModel>()

    companion object {
        const val TAG = "LOGGER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        setUpRecyclerView()
        binding.button.setOnClickListener {
            binding.txtInput.clear()
            clearList()
        }

        binding.txtInput.debounce(500) {
            lifecycleScope.launch(Dispatchers.IO) {
                val string = it.toString()
                Log.d(TAG, "String  = $string")
                viewModel.search(string)
            }
        }

        viewModel.onResult().observe(this) {
            searchListAdapter.submitList(it)
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerview.adapter = searchListAdapter
    }

    private fun clearList() {
        searchListAdapter.submitList(emptyList())
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}
