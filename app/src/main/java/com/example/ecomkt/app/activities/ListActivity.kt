package com.example.ecomkt.app.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ListAdapter
import com.example.ecomkt.app.adapters.createCommunityInfoAdapter
import com.example.ecomkt.app.models.CommunityInfoPresentation
import com.example.ecomkt.app.models.state.CommunityInfoListViewState
import com.example.ecomkt.app.viewmodels.ListViewModel
import com.example.ecomkt.databinding.ActivityListBinding
import com.example.ecomkt.app.util.*
import com.example.ecomkt.app.util.remove
import com.example.ecomkt.app.util.show
import com.example.ecomkt.app.util.showSnackbar
import com.example.ecomkt.app.util.startActivity
import dagger.hilt.android.AndroidEntryPoint
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private val listViewModel by viewModels<ListViewModel>()
    private lateinit var binding: ActivityListBinding
    private lateinit var communityAdapter: ListAdapter<CommunityInfoPresentation, RecyclerViewHolder<CommunityInfoPresentation>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listViewModel.getAllCommunityInfos()
        observeCommunityInfoListViewState()
        communityAdapter = createCommunityInfoAdapter(listViewModel)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.fab.setOnClickListener {
            startActivity<MainActivity>()
        }
    }

    private fun observeCommunityInfoListViewState() {
        listViewModel.communityInfoListViewState.observe(this, Observer {
            bindCommunities(it.communityInfoList)

            handleListError(it)
        })

    }

    private fun handleListError(communityInfoListViewState: CommunityInfoListViewState) {
        communityInfoListViewState.error?.run {
            showSnackbar(
                binding.recyclerView,
                getString(this.message),
                isError = true
            )
        }
    }

    private fun bindCommunities(infoList: List<CommunityInfoPresentation>?) {
        infoList?.let {

            with(binding.listLayout) {
                binding.progressBar.remove()
                if (infoList.isNotEmpty()) {
                    binding.recyclerView.apply {
                        adapter = communityAdapter.apply { submitList(infoList) }
                        initRecyclerViewWithLineDecoration(this@ListActivity)
                    }
                    binding.textView.hide()
                } else binding.textView.show()
            }
        }
    }
}