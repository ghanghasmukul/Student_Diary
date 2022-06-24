package com.example.studentdiary.ui.add_records

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studentdiary.R
import com.example.studentdiary.databinding.FragmentAddRecordsBinding
import com.example.studentdiary.retrofitApi.ApiInterface
import com.example.studentdiary.retrofitApi.CountryData
import com.example.studentdiary.retrofitApi.ModelCountryDetails
import com.example.studentdiary.roomdatabase.StudentDetails
import com.example.studentdiary.roomdatabase.StudentViewModel
import com.example.studentdiary.typeConverters.Converters
import com.google.android.gms.location.*
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.util.*


class AddRecordsFragment : Fragment() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var currentLocation: Location? = null
    private var studentViewModel: StudentViewModel = StudentViewModel()
    private val permissionCode = 101
    private var _binding: FragmentAddRecordsBinding? = null
    private val binding get() = _binding
    private var gpsEnabled: Boolean = false
    private var networkEnabled: Boolean = false
    private lateinit var alertDialog: AlertDialog
    private val IMAGE_REQUEST_CODE = 100
    var selectedImageUri: Uri? = null
    var imageURI: String? = null
    private lateinit var converter : Converters
  var byteArray: ByteArray  =  byteArrayOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): LinearLayout? {
        _binding = FragmentAddRecordsBinding.inflate(inflater, container, false)
        val root: LinearLayout? = binding?.root
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        binding?.btnSubmit?.setOnClickListener { onSubmitClick() }
        binding?.ivProfilePic?.setOnClickListener { onProfilePicClick() }
        getLocationUpdates()
        countryCode()
        return root
    }

    private fun onProfilePicClick() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_REQUEST_CODE)


    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            binding?.ivProfilePic?.setImageURI(data?.data)
            selectedImageUri = data?.data
            imageURI = selectedImageUri.toString()

            val inputStream = selectedImageUri?.let {
                requireContext().contentResolver.openInputStream(
                    it
                )
            }
            byteArray = inputStream?.readBytes()!!
//            val bitmap = converter.toBitmap(byteArray)
//            var photo =bitmap as Bitmap
//            photo = Bitmap.createScaledBitmap(photo, 100, 100, false)
//            val bytes = ByteArrayOutputStream()
//            photo.compress(CompressFormat.JPEG, 40, bytes)
//
//            val fo = FileOutputStream(f)
//            fo.write(bytes.toByteArray())
        }
    }



    override fun onStart() {
        super.onStart()
        checkGpsState()
        getLocationUpdates()
    }

    private fun countryCode() {
        val apiInterface = ApiInterface.create().getCode()
        apiInterface.enqueue(object : retrofit2.Callback<ModelCountryDetails> {
            override fun onResponse(
                call: Call<ModelCountryDetails>,
                response: Response<ModelCountryDetails>
            ) {
                val locale = Locale.getDefault().country.toString()
                val list = response.body()
                val arrList: ArrayList<CountryData> = list?.data as ArrayList<CountryData>
                for (item in arrList) {
                    if (item.code == locale) {
                        binding?.tvCountryCode?.text = item.dial_code
                        Log.i("tag", item.dial_code)
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
            binding?.tvAddressLineLocation?.text = list[0].locality
            Log.d(
                "details",
                "longitude ${currentLocation!!.longitude}, Latitude ${currentLocation!!.latitude}"
            )
        }
    }

    private fun onSubmitClick() {

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
                    requireContext(), StudentDetails(
                        name,
                        rollNo,
                        phoneNo,
                        binding!!.tvAddressLineLocation.text.toString(),
                        byteArray

                    )
                )
            }
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




private fun openViewRecordsFragment() {
    findNavController().navigate(R.id.navigation_view_records)
}

override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}

override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when (requestCode) {
        permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] ==
            PackageManager.PERMISSION_GRANTED
        ) {
            checkGpsState()
        }

    }
}

private fun checkGpsState() {
    val lm = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    if (!gpsEnabled && !networkEnabled) {
        alertDialog = AlertDialog.Builder(requireContext())
            .setMessage("Location Not Allowed")
            .setPositiveButton("Enable Location") { dialog, which ->
                dialog.dismiss()
                this.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton("Cancel", null)
            .show()
    } else {
        getLocationUpdates()
    }
}

}
