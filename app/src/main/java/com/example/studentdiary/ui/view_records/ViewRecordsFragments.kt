package com.example.studentdiary.ui.view_records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentdiary.adapters.ViewRecordsAdapter
import com.example.studentdiary.databinding.FragmentViewRecordsBinding
import com.example.studentdiary.roomdatabase.StudentDetails
import com.example.studentdiary.roomdatabase.StudentViewModel

class ViewRecordsFragments : Fragment() {

    private var _binding: FragmentViewRecordsBinding? = null

    private val binding get() = _binding!!
    private var studentViewModel: StudentViewModel = StudentViewModel()

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
        adapter1 = ViewRecordsAdapter(requireContext(),ArrayList<StudentDetails>())
        binding.rvViewRecords.layoutManager = LinearLayoutManager(context)
        binding.rvViewRecords.adapter = adapter1
        getData()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun getData() {
       studentViewModel.getAllUserData(requireContext()).observe(viewLifecycleOwner, Observer {
            adapter1.setData(it as ArrayList<StudentDetails>)
        })
    }
}