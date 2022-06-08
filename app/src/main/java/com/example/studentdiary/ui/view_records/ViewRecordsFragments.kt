package com.example.studentdiary.ui.view_records

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentdiary.R
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
        _binding = FragmentViewRecordsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)

        binding.rvViewRecords.layoutManager = LinearLayoutManager(context)
        adapter1 = ViewRecordsAdapter(requireContext(), ArrayList<StudentDetails>())
        binding.rvViewRecords.adapter = adapter1
        getData()
        return root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.searchbar, menu)

        super.onCreateOptionsMenu(menu, inflater)


    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getData() {
        studentViewModel.getAllUserData(requireContext()).observe(viewLifecycleOwner) {
            adapter1.setData(it as ArrayList<StudentDetails>)
        }
    }
}