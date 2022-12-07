package com.calibraint.sortlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.calibraint.sortlist.MainActivity.Companion.KEY_SORT_OPTION
import com.calibraint.sortlist.databinding.DialogSortingBinding

class SortOptionDialog(
    private val onSortOptionsSelected: (SortOption) -> Unit
) : DialogFragment() {

    lateinit var binding : DialogSortingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogSortingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        binding.confirmButton.setOnClickListener {
            onSortOptionSelected()
        }

        val currentSort = getSortOption()

        binding.sortOptions.check(
            when (currentSort) {
                ByName -> R.id.sortByName
                BySymbols -> R.id.sortBySymbols
                else -> R.id.noSort
            }
        )
    }

    private fun getSortOption(): SortOption {
        val localPreferences = activity?.getPreferences(Context.MODE_PRIVATE)

        return getSortOptionFromName(
            localPreferences?.getString(KEY_SORT_OPTION, "") ?: ""
        )
    }

    private fun onSortOptionSelected() {
        val selectedOption = binding.sortOptions.checkedRadioButtonId

        onSortOptionsSelected(
            when (selectedOption) {
                R.id.sortBySymbols -> BySymbols
                R.id.sortByName -> ByName
                else -> None
            }
        )

        dismissAllowingStateLoss()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}