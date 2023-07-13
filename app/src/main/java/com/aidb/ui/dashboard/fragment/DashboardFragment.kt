package com.aidb.ui.dashboard.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aidb.api.GET_EMAIL_ARTICLE
import com.aidb.databinding.FragmentDashboardBinding
import com.aidb.ui.dashboard.viewmodel.DashboardViewModel
import com.aidb.utils.network.Resource
import com.aidb.utils.network.Status
import com.aidb.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        mViewModel.getEmailArticles()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    private fun setObservers() {
        mViewModel.emailArticleResponse.observe(viewLifecycleOwner) {
            handleApiCallback(it)
        }
    }

    private fun handleApiCallback(apiResponse: Resource<Any>) {
        when (apiResponse.status) {
            Status.SUCCESS -> {
                //hide Progress
                when (apiResponse.apiConstant) {
                    GET_EMAIL_ARTICLE -> {
                        showToast(requireContext(), apiResponse.message.orEmpty())
                    }
                }
            }
            Status.LOADING -> {
                //show progress
            }
            Status.ERROR -> {
                //hide Progress
                showToast(requireContext(), apiResponse.message.orEmpty())
            }
            Status.RESOURCE -> {
                //hide Progress
                showToast(requireContext(), getString(apiResponse.resourceId!!))
            }
            else -> {}
        }
    }
}