package com.saptarshidas.secureops.ui.attendance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saptarshidas.secureops.R
import com.saptarshidas.secureops.base.BaseFragment
import com.saptarshidas.secureops.databinding.FragmentAttendanceDashboardBinding
import kotlinx.android.synthetic.main.fragment_attendance_dashboard.*

class AttendanceDashboardFragment : BaseFragment<FragmentAttendanceDashboardBinding, AttendanceViewModel>() {
    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAttendanceDashboardBinding {
        return FragmentAttendanceDashboardBinding.inflate(inflater, container, false)
    }

    override fun initViews() {

    }

    override fun initActionView() {
        cardMakeAttendance.setOnClickListener {
            navController.navigate(R.id.action_attendanceDashboardFragment_to_markAttendanceFragment)
        }
    }

    override fun getViewModel(): Class<AttendanceViewModel> {
        return AttendanceViewModel::class.java
    }

}