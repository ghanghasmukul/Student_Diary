package com.example.studentdiary.ui.view_records

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentdiary.MainActivity
import com.example.studentdiary.R
import com.example.studentdiary.adapters.ViewRecordsAdapter
import com.example.studentdiary.databinding.FragmentViewRecordsBinding
import com.example.studentdiary.roomdatabase.StudentDetails
import com.example.studentdiary.roomdatabase.StudentViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_view_records.*


class ViewRecordsFragments : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentViewRecordsBinding? = null
    private val binding get() = _binding!!
    private val studentViewModel: StudentViewModel by viewModels()
    private lateinit var adapter1: ViewRecordsAdapter
    lateinit var mAuth: FirebaseAuth
    private var dataList: ArrayList<StudentDetails> = arrayListOf()
    lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewRecordsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        mAuth = FirebaseAuth.getInstance()
        binding.rvViewRecords.layoutManager = LinearLayoutManager(context)
        adapter1 = ViewRecordsAdapter(requireContext(), ArrayList<StudentDetails>())
        binding.rvViewRecords.adapter = adapter1
        getData()
        return root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.searchbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchItem = menu.findItem(R.id.search)
        val logOut = menu.findItem(R.id.sign_out)
        val searchView = searchItem?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        logOut.setOnMenuItemClickListener {
            mAuth.signOut()
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(requireContext(),"Signing Out", Toast.LENGTH_SHORT).show()
            return@setOnMenuItemClickListener true
        }
    }


    private fun getData() {
        studentViewModel.getAllUserData(requireContext()).observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                rvViewRecords.visibility = View.GONE;
                tvEmptyDb.visibility = View.VISIBLE;
            } else {
                rvViewRecords.visibility = View.VISIBLE;
                tvEmptyDb.visibility = View.GONE;

                adapter1.setData(it as ArrayList<StudentDetails>)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchDatabase(newText)
        }
        return true
    }

    private fun searchDatabase(query: String?) {
        if (query != null) {
            studentViewModel.searchDatabase(query)?.observe(this) { list ->
                list.let {
                    adapter1.setData(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}