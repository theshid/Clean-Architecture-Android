package com.example.ecomkt.app.models.state

import com.example.ecomkt.app.models.PostResponsePresentation

data class PostResponseViewState(
    val isComplete: Boolean,
    val error: Error?,
    val response:PostResponsePresentation?
)
