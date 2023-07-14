package com.aidb.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aidb.R
import com.aidb.databinding.AdapterEmailArticleBinding
import com.aidb.ui.dashboard.model.EmailArticleResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ArticleAdapter(
    private val articleList: ArrayList<EmailArticleResponse.Result?>?,
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AdapterEmailArticleBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(articleList?.get(position))

    override fun getItemCount(): Int =
        if (!articleList.isNullOrEmpty()) articleList.size else 0

    inner class ViewHolder(private val binding: AdapterEmailArticleBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EmailArticleResponse.Result?) {
            binding.tvTitle.text = item?.title.orEmpty()
            binding.tvDesc.text = item?.source.orEmpty()
            val imageUrl = item?.media?.get(0)?.mediaMetadata?.get(0)?.url
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(context)
                    .load(imageUrl)
                    .apply(RequestOptions().circleCrop())
                    .placeholder(R.drawable.ic_article_placeholer)
                    .into(binding.imgArticle)
            } else {
                binding.imgArticle.setImageResource(R.drawable.ic_article_placeholer)
            }
        }
    }
}