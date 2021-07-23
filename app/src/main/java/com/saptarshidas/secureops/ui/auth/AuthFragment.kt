package com.saptarshidas.secureops.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.saptarshidas.secureops.R
import com.saptarshidas.secureops.base.BaseFragment
import com.saptarshidas.secureops.data.exchanges.AuthRequest
import com.saptarshidas.secureops.data.network.Resource
import com.saptarshidas.secureops.databinding.FragmentAuthBinding
import com.saptarshidas.secureops.utils.checkEdTextValidation
import com.saptarshidas.secureops.utils.enable
import com.saptarshidas.secureops.utils.handleApiError
import com.saptarshidas.secureops.utils.snackBar
import kotlinx.android.synthetic.main.fragment_auth.*
import kotlinx.coroutines.launch

class AuthFragment : BaseFragment<FragmentAuthBinding, AuthViewModel>() {

    private val TAG = "AuthFragment"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vModel.authLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> {
                    // on valid username password

                    if(it.data.LoginStatus.equals("Success")){

                        lifecycleScope.launch {
                            requireView().snackBar("Login Successful")
                            preferencesHelper.setEmployeeId(it.data.EmpCode)
                            preferencesHelper.setName(it.data.Name)
                            preferencesHelper.setDesignation(it.data.Designation)
                            preferencesHelper.setClientName(it.data.ClientName)
                            navController.navigate(R.id.action_authFragment_to_attendanceDashboardFragment)
                        }

                    }else{
                        requireView().snackBar("Login failed")
                    }
                }

                is Resource.Error -> {handleApiError(it){}}
            }
        })
    }

    override fun initViews() {
        btnLogin.enable(false)

        edTextPassword.addTextChangedListener{
            val username = edTextUsername.text.toString().trim()
            btnLogin.enable(username.isNotEmpty() && it.toString().isNotEmpty())
        }
    }

    override fun initActionView() {
        btnLogin.setOnClickListener {

            val editTextList = arrayListOf<EditText>(edTextUsername, edTextPassword)

            if(checkEdTextValidation(editTextList)){

                vModel.login(
                    AuthRequest(edTextUsername.text.toString(), edTextPassword.text.toString())
                )
            }
        }

        btnForgotPass.setOnClickListener {
            TODO("To be implemented")
        }
    }

    override fun getViewModel(): Class<AuthViewModel> {
        return AuthViewModel::class.java
    }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAuthBinding {
        return FragmentAuthBinding.inflate(inflater, container, false)
    }

}