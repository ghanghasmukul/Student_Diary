package com.example.studentdiary.ui.add_records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studentdiary.databinding.FragmentAddRecordsBinding
import com.example.studentdiary.roomdatabase.StudentDetails
import com.example.studentdiary.roomdatabase.StudentViewModel

class AddRecordsFragment : Fragment() {
    private lateinit var studentList:ArrayList<StudentDetails>
    private  var studentViewModel : StudentViewModel = StudentViewModel()

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
            if (binding.enterNameET.isEmpty() || binding.phoneNoET.isEmpty() || binding.rollNoET.isEmpty()) {
                if (binding.enterNameET.isEmpty()) {
                    binding.enterNameET.error = "Name can't be empty"
                    binding.enterNameET.isErrorEnabled = true
                }
                if (binding.rollNoET.isEmpty()) {
                    binding.rollNoET.error = "Roll no. can't be empty"
                    binding.enterNameET.isErrorEnabled = true
                }
                if (binding.phoneNoET.isEmpty()) {
                    binding.phoneNoET.error = "Phone no. can't be empty"
                    binding.enterNameET.isErrorEnabled = true
                }
            }
                else
                {
                    studentViewModel.insert(
                        requireContext(),
                        StudentDetails(name, roll_no, phone_no)
                    )
                }

            }





        return root
    }

    fun setData(userList:ArrayList<StudentDetails>)
    {
        this.studentList=userList

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}