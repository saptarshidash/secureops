package com.saptarshidas.secureops.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.saptarshidas.secureops.data.local.AppPreferencesHelper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseFragment<Binding: ViewBinding,VM : AndroidViewModel> : Fragment() {

    private  val TAG = "BaseFragment"

    protected lateinit var viewBinding: Binding

    protected lateinit var vModel: VM

    protected lateinit var navController: NavController

    protected lateinit var preferencesHelper: AppPreferencesHelper

    protected  var empId: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // set view binding
        viewBinding = setBinding(inflater, container)

        vModel = ViewModelProvider(this).get(getViewModel())

        preferencesHelper = AppPreferencesHelper(requireContext())

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        initViews()
        initActionView()

        lifecycleScope.launch {
            empId = preferencesHelper.getEmployeeId().first()

        }





    }

    public abstract fun setBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    public abstract fun initViews()

    public abstract fun initActionView()

    public abstract fun getViewModel(): Class<VM>

}