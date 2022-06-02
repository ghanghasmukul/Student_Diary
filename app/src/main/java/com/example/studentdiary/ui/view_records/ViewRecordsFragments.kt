package com.example.studentdiary.ui.view_records

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentdiary.Adapters.ViewRecordsAdapter
import com.example.studentdiary.databinding.FragmentViewRecordsBinding

class ViewRecordsFragments : Fragment() {

    private var _binding: FragmentViewRecordsBinding? = null

    private val binding get() = _binding!!
    private lateinit var layoutManager1: RecyclerView.LayoutManager
    private lateinit var adapter1: ViewRecordsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewRecordsViewModel =
            ViewModelProvider(this)[ViewRecordsViewModel::class.java]

        _binding = FragmentViewRecordsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.rvViewRecords.layoutManager = LinearLayoutManager(context)
        val adapter = ViewRecordsAdapter()
        binding.rvViewRecords.layoutManager = LinearLayoutManager(context)
        binding.rvViewRecords.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}