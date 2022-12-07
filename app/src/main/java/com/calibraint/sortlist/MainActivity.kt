package com.calibraint.sortlist

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.calibraint.sortlist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val items = mutableListOf<String>()

    private lateinit var binding: ActivityMainBinding
    private val adapter = ItemListAdapter(mutableListOf())

    companion object {
        const val KEY_SORT_OPTION = "sort_option"
    }

    private val localPreferences by lazy { getPreferences(Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.itemList.adapter = adapter
        binding.itemList.layoutManager = LinearLayoutManager(this)

        binding.addTextButton.setOnClickListener {
            val text = binding.inputText
            items.add(text.text.toString())
            refreshData()
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id : Int = item.itemId
        when(id){
            R.id.action_filter -> {
                showFilterAndSortingDialog()
            }

        }
        return  super.onOptionsItemSelected(item)
    }

    private fun showFilterAndSortingDialog() {
        val dialog = SortOptionDialog { sortOption ->
            saveSortOption(sortOption)
            refreshData()
        }
        dialog.show(supportFragmentManager, null)
    }

    private fun refreshData() {
        adapter.setData(items, getSortOption())
    }

    private fun getSortOption(): SortOption {
        return getSortOptionFromName(localPreferences.getString(KEY_SORT_OPTION, "") ?: "")
    }

    private fun saveSortOption(sortOption: SortOption) {
        localPreferences.edit()
            .putString(KEY_SORT_OPTION, sortOption.name)
            .apply()
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_filter, menu)
        return true
    }

}