package com.example.ecomkt.app.adapters

import android.view.View
import com.example.ecomkt.app.models.CommunityInfoPresentation
import com.example.ecomkt.databinding.RowItemBinding
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder

class CommunityInfoViewHolder(view: View) : RecyclerViewHolder<CommunityInfoPresentation>(view) {
    val binding = RowItemBinding.bind(view)
}