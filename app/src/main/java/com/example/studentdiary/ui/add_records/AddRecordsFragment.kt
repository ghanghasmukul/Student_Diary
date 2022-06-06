package com.example.studentdiary.ui.add_records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studentdiary.R
import com.example.studentdiary.databinding.FragmentAddRecordsBinding
import com.example.studentdiary.roomdatabase.StudentDetails
import com.example.studentdiary.roomdatabase.StudentViewModel


class AddRecordsFragment : Fragment() {

    private var studentViewModel: StudentViewModel = StudentViewModel()
    private var _binding: FragmentAddRecordsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRecordsBinding.inflate(inflater, container, false)
        val root: View = binding.root

       onSubmitClick()
    //    onUpdateClick()

        return root
    }

    private fun onUpdateClick() {
     val name = requireArguments().getString("name")
        val phoneNo = requireArguments().getString("phoneNo")
        val rollNo = requireArguments().getString("rollNo")



    }


    private fun onSubmitClick() {
        binding.btnSubmit.setOnClickListener {
            val name = binding.etEnterName.editText?.text.toString()
            val rollNo = binding.etRollNo.editText?.text.toString()
            val phoneNo = binding.etPhoneNo.editText?.text.toString()
            if (name.isEmpty() || phoneNo.isEmpty() || rollNo.isEmpty()) {
                if (name.isEmpty()) {
                    binding.etEnterName.error = "Name can't be empty"
                    Toast.makeText(requireContext(), "empty name", Toast.LENGTH_SHORT).show()
                    binding.etEnterName.isErrorEnabled = true
                } else {
                    binding.etEnterName.error = null
                    binding.etEnterName.isErrorEnabled = false

                }
                if (rollNo.isEmpty()) {
                    Toast.makeText(requireContext(), "empty roll", Toast.LENGTH_SHORT).show()

                    binding.etRollNo.error = "Roll no. can't be empty"
                    binding.etEnterName.isErrorEnabled = true
                } else {
                    binding.etRollNo.error = null
                    binding.etRollNo.isErrorEnabled = false
                }
                if (phoneNo.isEmpty()) {
                    Toast.makeText(requireContext(), "empty phone", Toast.LENGTH_SHORT).show()
                    binding.etPhoneNo.error = "Phone no. can't be empty"
                    binding.etEnterName.isErrorEnabled = true
                } else {
                    binding.etPhoneNo.error = null
                    binding.etPhoneNo.isErrorEnabled = false
                }
            } else {

                studentViewModel.insert(
                    requireContext(),
                    StudentDetails(name, rollNo, phoneNo)
                )
                openViewRecordsFragment()
                Toast.makeText(requireContext(),"Data Saved Successfully",Toast.LENGTH_LONG).show()
                binding.etEnterName.editText?.text = null
                binding.etPhoneNo.editText?.text = null
                binding.etRollNo.editText?.text = null
            }
        }
    }

    private fun openViewRecordsFragment() {
        findNavController().navigate(R.id.navigation_view_records)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}