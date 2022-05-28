package com.example.ecomkt.app.menu

import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.example.ecomkt.R
import com.example.ecomkt.app.activities.MainActivity
import com.example.ecomkt.app.models.CommunityInfoPresentation
import com.example.ecomkt.app.viewmodels.ListViewModel
import com.example.ecomkt.app.util.Common
import com.example.ecomkt.app.util.GsonParser
import com.example.ecomkt.app.util.startActivity

class PopupClickListener(
    val communityInfoPresentation: CommunityInfoPresentation,
    private val listViewModel: ListViewModel,
    private val view: View
) : PopupMenu.OnMenuItemClickListener {
    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> {
                val dataToSend = GsonParser.gsonParser?.toJson(communityInfoPresentation)
                view.context.startActivity<MainActivity> {
                    putExtra(Common.EXTRA_TO_MAIN, dataToSend)
                }
                return true
            }
            R.id.action_delete -> {
                listViewModel.deleteCommunityInfo(communityInfoPresentation.id)
                return true
            }
            else -> {}
        }
        return false
    }
}