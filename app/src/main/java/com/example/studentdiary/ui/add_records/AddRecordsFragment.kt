package com.example.studentdiary.ui.add_records

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.os.Parcel
import android.os.Parcelable
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studentdiary.R
import com.example.studentdiary.retrofitApi.ApiInterface
import com.example.studentdiary.retrofitApi.ModelCountryDetails
import com.example.studentdiary.retrofitApi.CountryData

import com.example.studentdiary.databinding.FragmentAddRecordsBinding

import com.example.studentdiary.roomdatabase.StudentDetails
import com.example.studentdiary.roomdatabase.StudentViewModel
import com.google.android.gms.location.*
import retrofit2.Call
import retrofit2.Response

import java.util.*
import kotlin.collections.ArrayList


class AddRecordsFragment() : Fragment(), Parcelable {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var currentLocation: Location? = null
    private var studentViewModel: StudentViewModel = StudentViewModel()
    private val permissionCode = 101
    private var _binding: FragmentAddRecordsBinding? = null
    private val binding get() = _binding

    constructor(parcel: Parcel) : this() {
        currentLocation = parcel.readParcelable(Location::class.java.classLoader)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): LinearLayout? {
        _binding = FragmentAddRecordsBinding.inflate(inflater, container, false)
        val root: LinearLayout? = binding?.root
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        onSubmitClick()
        getLocationUpdates()
        apiResponse()

   return root
    }

    private fun apiResponse() {
        val apiInterface = ApiInterface.create().getCode()
        apiInterface.enqueue(object : retrofit2.Callback<ModelCountryDetails> {
            override fun onResponse(call: Call<ModelCountryDetails>, response: Response<ModelCountryDetails>) {
                val locale = Locale.getDefault().country.toString()

                var list = response.body()
                val arrList: ArrayList<CountryData> = list?.data as ArrayList<CountryData>
                for (item in arrList){
                    if (item.code == locale){
                        binding?.tvCountryCode?.text  = item.dial_code
                        Log.i("tag",item.dial_code)
                        break
                    }
                }

            }

            override fun onFailure(call: Call<ModelCountryDetails>, t: Throwable) {
                Log.i("tag", "OnFailure")
            }

        })
    }


    private fun getLocationUpdates() {

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
            fetchLoc()

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

            val geocoder = Geocoder(context, Locale.getDefault())
            val list =
                geocoder.getFromLocation(
                    currentLocation!!.latitude,
                    currentLocation!!.longitude,
                    1
                ) as List<Address>
            binding?.tvAddressLineLocation?.text   = list[0].locality
            Log.d(
                "details",
                "longitude ${currentLocation!!.longitude}, Latitude ${currentLocation!!.latitude}"
            )
        }
    }

    private fun onSubmitClick() {

        binding?.let {
            binding!!.btnSubmit.setOnClickListener {
                val name = binding!!.etEnterName.editText?.text.toString()
                val rollNo = binding!!.etRollNo.editText?.text.toString()
                val phoneNo = binding!!.etPhoneNo.editText?.text.toString()
                if (name.isEmpty() || phoneNo.isEmpty() || rollNo.isEmpty()) {
                    if (name.isEmpty()) {
                        binding!!.etEnterName.error = "Name can't be empty"
                        Toast.makeText(requireContext(), "empty name", Toast.LENGTH_SHORT).show()
                        binding!!.etEnterName.isErrorEnabled = true
                    } else {
                        binding!!.etEnterName.error = null
                        binding!!.etEnterName.isErrorEnabled = false

                    }
                    if (rollNo.isEmpty() || rollNo.length != 4) {
                        Toast.makeText(requireContext(), "empty roll", Toast.LENGTH_SHORT).show()

                        binding!!.etRollNo.error = "Roll no. can't be empty or less than 4 digits"
                        binding!!.etEnterName.isErrorEnabled = true
                    } else {
                        binding!!.etRollNo.error = null
                        binding!!.etRollNo.isErrorEnabled = false
                    }
                    if (phoneNo.isEmpty() || phoneNo.length != 10) {
                        Toast.makeText(requireContext(), "empty phone", Toast.LENGTH_SHORT).show()
                        binding!!.etPhoneNo.error = "Phone no. can't be empty"
                        binding!!.etEnterName.isErrorEnabled = true
                    } else {
                        binding!!.etPhoneNo.error = null
                        binding!!.etPhoneNo.isErrorEnabled = false
                    }
                } else {
                    if (rollNo.length != 4 || phoneNo.length != 10) {
                        if (rollNo.length != 4) {
                            binding!!.etRollNo.error = "Roll no. must be of 4 digits"
                            binding!!.etRollNo.isErrorEnabled = true
                        }
                        if (phoneNo.length != 10) {
                            binding!!.etPhoneNo.error = "Phone no. must contain 10 digits"
                            binding!!.etPhoneNo.isErrorEnabled = true
                        }
                    } else {
                        binding!!.etPhoneNo.error = null
                        binding!!.etRollNo.error = null
                        binding!!.etPhoneNo.isErrorEnabled = false
                        binding!!.etRollNo.isErrorEnabled = false

                        studentViewModel.insert(
                            requireContext(),
                            StudentDetails(
                                name,
                                rollNo,
                                phoneNo,
                                binding!!.tvAddressLineLocation.text.toString()
                            )
                        )
                        openViewRecordsFragment()
                        Toast.makeText(
                            requireContext(),
                            "Data Saved Successfully",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        binding!!.etEnterName.editText?.text = null
                        binding!!.etPhoneNo.editText?.text = null
                        binding!!.etRollNo.editText?.text = null
                    }

                }
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(currentLocation, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddRecordsFragment> {
        override fun createFromParcel(parcel: Parcel): AddRecordsFragment {
            return AddRecordsFragment(parcel)
        }

        override fun newArray(size: Int): Array<AddRecordsFragment?> {
            return arrayOfNulls(size)
        }
    }


}






