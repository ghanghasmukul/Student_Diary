package com.example.studentdiary.ui.add_records

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studentdiary.databinding.FragmentAddRecordsBinding
import com.example.studentdiary.roomdatabase.StudentDetails
import com.example.studentdiary.roomdatabase.StudentViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap

import java.util.*


class AddRecordsFragment : Fragment() {
    val REQUEST_LOCATION = 100
    private var googleMap: GoogleMap? = null
    private val permissionCode = 101
    private var currentLocation: Location? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationManager: LocationManager? = null
    private var gpsEnabled: Boolean = false
    private var networkEnabled: Boolean = false

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
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        onSubmitClick()


        return root
    }


    private fun onSubmitClick() {
        //test
        binding.submitBtn.setOnClickListener {


            val name = binding.enterNameET.editText?.text.toString()
            val roll_no = binding.rollNoET.editText?.text.toString()
            val phone_no = binding.phoneNoET.editText?.text.toString()
            if (name.isEmpty() || phone_no.isEmpty() || roll_no.isEmpty()) {
                if (name.isEmpty()) {
                    binding.enterNameET.error = "Name can't be empty"
                    Toast.makeText(requireContext(), "empty name", Toast.LENGTH_SHORT).show()
                    binding.enterNameET.isErrorEnabled = true
                } else {
                    binding.enterNameET.error = null
                    binding.enterNameET.isErrorEnabled = false

                }
                if (roll_no.isEmpty()) {
                    Toast.makeText(requireContext(), "empty roll", Toast.LENGTH_SHORT).show()

                    binding.rollNoET.error = "Roll no. can't be empty"
                    binding.enterNameET.isErrorEnabled = true
                } else {
                    binding.rollNoET.error = null
                    binding.rollNoET.isErrorEnabled = false
                }
                if (phone_no.isEmpty()) {
                    Toast.makeText(requireContext(), "empty phone", Toast.LENGTH_SHORT).show()
                    binding.phoneNoET.error = "Phone no. can't be empty"
                    binding.enterNameET.isErrorEnabled = true
                } else {
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
    }

    fun setData(userList: ArrayList<StudentDetails>) {
        this.studentList = userList

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}