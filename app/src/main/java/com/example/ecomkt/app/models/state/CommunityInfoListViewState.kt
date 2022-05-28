package com.example.ecomkt.app.models.state

import com.example.ecomkt.app.models.CommunityInfoPresentation

data class CommunityInfoListViewState(
    val isLoading: Boolean,
    val error: Error?,
    val communityInfoList: List<CommunityInfoPresentation>?
)
