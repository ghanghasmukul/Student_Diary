package com.example.studentdiary.ui.add_records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studentdiary.databinding.FragmentAddRecordsBinding
import com.example.studentdiary.roomdatabase.StudentDetails
import com.example.studentdiary.roomdatabase.StudentViewModel

class AddRecordsFragment : Fragment() {
    private lateinit var studentList: ArrayList<StudentDetails>
    private var studentViewModel: StudentViewModel = StudentViewModel()

    private var _binding: FragmentAddRecordsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val addRecordsViewModel =
            ViewModelProvider(this)[AddRecordsViewModel::class.java]

        _binding = FragmentAddRecordsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val name = binding.enterNameET.editText
        val roll_no = binding.rollNoET.editText
        val phone_no = binding.phoneNoET.editText



        binding.submitBtn.setOnClickListener {

            val name = name?.text.toString()
            val roll_no = roll_no?.text.toString()
            val phone_no = phone_no?.text.toString()
            if (name.isEmpty() || phone_no.isEmpty() || roll_no.isEmpty()) {
                if (name.isEmpty()) {
                    binding.enterNameET.error = "Name can't be empty"
                    Toast.makeText(requireContext(), "empty name", Toast.LENGTH_SHORT).show()
                    binding.enterNameET.isErrorEnabled = true
                }
                else{
                    binding.enterNameET.error = null
                    binding.enterNameET.isErrorEnabled = false

                }
                if (roll_no.isEmpty()) {
                    Toast.makeText(requireContext(), "empty roll", Toast.LENGTH_SHORT).show()

                    binding.rollNoET.error = "Roll no. can't be empty"
                    binding.enterNameET.isErrorEnabled = true
                }
                else{
                    binding.rollNoET.error = null
                    binding.rollNoET.isErrorEnabled = false
                }
                if (phone_no.isEmpty()) {
                    Toast.makeText(requireContext(), "empty phone", Toast.LENGTH_SHORT).show()
                    binding.phoneNoET.error = "Phone no. can't be empty"
                    binding.enterNameET.isErrorEnabled = true
                }
                else{
                    binding.phoneNoET.error = null
                    binding.phoneNoET.isErrorEnabled = false
                }
            } else {

                studentViewModel.insert(
                    requireContext(),
                    StudentDetails(name, roll_no, phone_no)
                )
            }

        }


        return root
    }

    fun setData(userList: ArrayList<StudentDetails>) {
        this.studentList = userList

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}