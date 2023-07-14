package com.aidb.ui.dashboard.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aidb.api.GET_EMAIL_ARTICLE
import com.aidb.databinding.FragmentDashboardBinding
import com.aidb.ui.dashboard.adapter.ArticleAdapter
import com.aidb.ui.dashboard.model.EmailArticleResponse
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
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var articleList: ArrayList<EmailArticleResponse.Result?>

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
        setListener()
        setObservers()
        initRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListener() {
        binding.swipeToRefresh.setOnRefreshListener {
            articleList.clear()
            articleAdapter.notifyDataSetChanged()
            mViewModel.getEmailArticles()
            binding.swipeToRefresh.isRefreshing = false
        }
    }

    private fun setObservers() {
        mViewModel.emailArticleResponse.observe(viewLifecycleOwner) {
            handleApiCallback(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleApiCallback(apiResponse: Resource<Any>) {
        when (apiResponse.status) {
            Status.SUCCESS -> {
                //hide Progress
                when (apiResponse.apiConstant) {
                    GET_EMAIL_ARTICLE -> {
                        articleList.clear()
                        val result = apiResponse.data as EmailArticleResponse?
                        if (!result?.results.isNullOrEmpty()) {
                            articleList.addAll(result?.results!!)
                        }
                        articleAdapter.notifyDataSetChanged()
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
        }
    }

    private fun initRecyclerView() {
        articleList = arrayListOf()
        binding.rcvArticle.apply {
            layoutManager = LinearLayoutManager(requireContext())
            articleAdapter = ArticleAdapter(articleList)
            val verticalDecorator =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(verticalDecorator)
            adapter = articleAdapter
        }
    }
}