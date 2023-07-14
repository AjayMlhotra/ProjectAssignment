package com.aidb.ui.signup.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aidb.R
import com.aidb.databinding.FragmentSignupBinding
import com.aidb.ui.signup.viewmodel.SignUpViewModel
import com.aidb.utils.network.Resource
import com.aidb.utils.network.Status
import com.aidb.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        setObservers()
    }

    private fun setListener() {
        binding.btnSignup.setOnClickListener {
            userSignup()
        }
    }

    private fun userSignup() {
        mViewModel.signUpRequest.name = binding.edtUserName.text.toString()
        mViewModel.signUpRequest.email = binding.edtEmailId.text.toString()
        mViewModel.signUpRequest.password = binding.txtPassword.text.toString()
        mViewModel.validateSignUpFields()
    }

    private fun setObservers() {
        mViewModel.isValidated.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(R.id.action_signup_fragment_to_dashboard_graph)
        }

        mViewModel.validationError.observe(viewLifecycleOwner) {
            validationCallback(it)
        }
    }

    private fun validationCallback(apiResponse: Resource<Any>) {
        if (apiResponse.status == Status.ERROR) {
            showToast(requireContext(), apiResponse.message.orEmpty())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}