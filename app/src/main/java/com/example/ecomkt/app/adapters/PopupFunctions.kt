package com.example.ecomkt.app.adapters

import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.example.ecomkt.R
import com.example.ecomkt.app.menu.PopupClickListener
import com.example.ecomkt.app.models.CommunityInfoPresentation
import com.example.ecomkt.app.viewmodels.ListViewModel

fun showPopupMenu(
    view: View,
    communityInfoPresentation: CommunityInfoPresentation,
    listViewModel: ListViewModel
) {
    val popup = PopupMenu(view.context, view)
    val inflater = popup.menuInflater
    inflater.inflate(R.menu.menu_list, popup.menu)
    popup.setOnMenuItemClickListener(
        PopupClickListener(
            communityInfoPresentation,
            listViewModel,
            view
        )
    )

    popup.show()
}