package com.example.ecomkt.app.adapters

import com.example.ecomkt.R
import com.example.ecomkt.app.models.CommunityInfoPresentation
import com.example.ecomkt.app.viewmodels.ListViewModel
import com.example.ecomkt.app.util.hide
import com.example.ecomkt.app.util.toBitmap
import me.ibrahimyilmaz.kiel.adapterOf

internal inline fun createCommunityInfoAdapter(listViewModel: ListViewModel) = adapterOf<CommunityInfoPresentation> {
    diff(
        areItemsTheSame = { old, new -> old === new },
        areContentsTheSame = { old, new -> old.communityName == new.communityName }
    )
    register(
        layoutResource = R.layout.row_item,
        viewHolder = ::CommunityInfoViewHolder,
        onBindViewHolder = { communityInfoViewHolder, _, communityInfoPresentation ->
            communityInfoViewHolder.binding.communityName.text = communityInfoPresentation.communityName
            communityInfoViewHolder.binding.connectedEcg.text = communityInfoPresentation.connectedToECG
            communityInfoViewHolder.binding.accessibility.text = communityInfoPresentation.accessibility.toString()
            communityInfoViewHolder.binding.distanceEcom.text = communityInfoPresentation.distanceToECOM.toString()
            communityInfoViewHolder.binding.geographicalDistrict.text = communityInfoPresentation.geographicalDistrict.toString()
            communityInfoViewHolder.binding.latitude.text = communityInfoPresentation.latitude.toString()
            communityInfoViewHolder.binding.longitude.text = communityInfoPresentation.longitude.toString()
            communityInfoViewHolder.binding.licenseDate.text = communityInfoPresentation.licenseDate
            communityInfoViewHolder.binding.photo.setImageBitmap(communityInfoPresentation.image.toBitmap())
            if (!communityInfoPresentation.sentServer){
                communityInfoViewHolder.binding.imageStatus.setImageResource(R.drawable.ic_error)
                communityInfoViewHolder.binding.textviewStatus.text = "Item not sent"
                communityInfoViewHolder.binding.btnResend.hide()
            }else{
                communityInfoViewHolder.binding.imageStatus.setImageResource(R.drawable.ic_check_circle)
                communityInfoViewHolder.binding.textviewStatus.text = "Item sent"
                communityInfoViewHolder.binding.btnResend.hide()
            }
            communityInfoViewHolder.binding.ripple.setOnClickListener {
                showPopupMenu(it,communityInfoPresentation,listViewModel)
            }

        }
    )
}