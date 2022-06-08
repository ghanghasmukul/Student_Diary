package com.example.studentdiary.ui.add_records

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studentdiary.R
import com.example.studentdiary.databinding.FragmentAddRecordsBinding
import com.example.studentdiary.roomdatabase.StudentDetails
import com.example.studentdiary.roomdatabase.StudentViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import java.util.*


class AddRecordsFragment : Fragment() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var currentLocation: Location? = null
    private var studentViewModel: StudentViewModel = StudentViewModel()
    private val permissionCode = 101
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
        getLocationUpdates()
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fusedLocationProviderClient = context.let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }
        getLocationUpdates()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLocationUpdates()

    }

    private  fun getLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode
            )
        }
            val task = fusedLocationProviderClient.lastLocation
            task.addOnSuccessListener {
                currentLocation = it
                val progress = ProgressDialog(requireContext())
                progress.setTitle("Loading")
                progress.setMessage("Wait while loading...")
                progress.setCancelable(false) // disable dismiss by tapping outside of the dialog

                progress.show()
                fetchLoc()
                progress.dismiss()

            }

            val mLocationRequest = LocationRequest.create()
            mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            mLocationRequest.interval = 0
            mLocationRequest.fastestInterval = 0

            val callback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    currentLocation = p0.lastLocation
                    fusedLocationProviderClient.removeLocationUpdates(this)
                }
            }
            fusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest,
                callback,
                Looper.getMainLooper()
            )
        }


    private fun fetchLoc() {
        currentLocation?.let {
            val latLng = LatLng(it.latitude, it.longitude)
            val geocoder = Geocoder(context, Locale.getDefault())
            val list: List<Address> =
                geocoder.getFromLocation(currentLocation!!.latitude, currentLocation!!.longitude, 1) as List<Address>
            binding.tvLocalityLocation.text = list[0].locality
            binding.tvAddressLineLocation.text = list[0].getAddressLine(0)
            Log.d("details", "longitude ${latLng.longitude}, Latitude ${latLng.latitude}")
        }
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
                    StudentDetails(
                        name,
                        rollNo,
                        phoneNo,
                        binding.tvAddressLineLocation.text.toString(),
                        binding.tvLocalityLocation.text.toString()
                    )
                )
                openViewRecordsFragment()
                Toast.makeText(requireContext(), "Data Saved Successfully", Toast.LENGTH_LONG)
                    .show()
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



