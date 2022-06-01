package com.example.studentdiary.ui.view_records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studentdiary.databinding.FragmentViewRecordsBinding

class ViewRecordsFragments : Fragment() {

    private var _binding: FragmentViewRecordsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewRecordsViewModel =
            ViewModelProvider(this).get(ViewRecordsViewModel::class.java)

        _binding = FragmentViewRecordsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textViewRecords
        viewRecordsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}