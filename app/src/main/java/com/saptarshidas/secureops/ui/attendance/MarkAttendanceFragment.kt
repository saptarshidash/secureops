package com.saptarshidas.secureops.ui.attendance

import android.Manifest.permission
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.google.android.gms.location.*
import com.saptarshidas.secureops.base.BaseFragment
import com.saptarshidas.secureops.data.exchanges.MarkAttendanceRequest
import com.saptarshidas.secureops.data.network.Resource
import com.saptarshidas.secureops.databinding.FragmentMarkAttendanceBinding
import com.saptarshidas.secureops.utils.handleApiError
import com.saptarshidas.secureops.utils.progressDialog
import com.saptarshidas.secureops.utils.snackBar
import kotlinx.android.synthetic.main.fragment_mark_attendance.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.sql.Date
import com.saptarshidas.secureops.R


class MarkAttendanceFragment : BaseFragment<FragmentMarkAttendanceBinding, AttendanceViewModel>() {


    private val TAG = "MarkAttendanceFragment"
    private lateinit var codeScanner: CodeScanner

    private val currentLocation: Location? = null
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private var locationProviderClient: FusedLocationProviderClient? = null

    private var longitude: Double? = null
    private  var latitude: Double? = null

    private lateinit var dialog: ProgressDialog

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }

        // location
        locationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        locationRequest = LocationRequest.create()

        getCurrentLocation()


        vModel.markAttendanceLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> {
                    requireView().snackBar(it.data.AttendanceStatus)
                    dialog.dismiss()
                    navController.navigate(R.id.action_markAttendanceFragment_to_attendanceDashboardFragment)
                }
                is Resource.Error -> {
                    handleApiError(it){}
                    dialog.dismiss()
                }
            }
        })
    }


    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMarkAttendanceBinding {
        return FragmentMarkAttendanceBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        // code scan
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scanner_view)
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {

                val data = it.text.split(" ")
                val site = data[0]
                val scannedLat = data[1].toDouble()
                val scannedLong = data[2].toDouble()
                val difference =
                    getDistanceFromLatLonInKm(scannedLat, scannedLong, latitude!!, longitude!!)

                lifecycleScope.launch {
                    if (difference < 3) {
                        val request = MarkAttendanceRequest(
                            data[0],
                            preferencesHelper.getEmployeeId().first().toString(),
                            latitude.toString(),
                            longitude.toString(),
                            preferencesHelper.getEmployeeId().first().toString()
                        )
                        dialog = progressDialog("Checking in", "Please wait")
                        dialog.show()
                        vModel.markAttendance(request)
                    }else{
                        requireView().snackBar("Too far from QR code")
                    }




                    Log.d(TAG, "Difference $difference KM ")


                }
            }

        }
    }

    override fun initActionView() {

    }

    override fun getViewModel(): Class<AttendanceViewModel> {
        return AttendanceViewModel::class.java
    }

    fun getCurrentLocation() {
        dialog = progressDialog("Fetching location", "Please wait")
        dialog.show()
        if (ActivityCompat.checkSelfPermission(requireContext(), permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(), permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    permission.ACCESS_COARSE_LOCATION,
                    permission.ACCESS_FINE_LOCATION
                ), 1
            )
        } else {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    longitude = locationResult.lastLocation.longitude
                    latitude = locationResult.lastLocation.latitude
                    Log.d(TAG, "onLocationResult: "+latitude+" "+longitude)
                    dialog.dismiss()
                }
            }
            locationProviderClient!!.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
        }


    }

    private fun getDistanceFromLatLonInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        var R = 6371; // Radius of the earth in km
        var dLat = deg2rad(lat2-lat1);  // deg2rad below
        var dLon = deg2rad(lon2-lon1);
        var a =
            Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                    Math.sin(dLon/2) * Math.sin(dLon/2)
        ;
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        var d = R * c; // Distance in km
        return d;
    }

    fun deg2rad(deg: Double): Double {
        return deg * (Math.PI/180)
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

}