package com.example.sarycatalogtask.ui.dialogs

import com.example.sarycatalogtask.R
import com.example.sarycatalogtask.data.banners.BannerResult
import com.example.sarycatalogtask.databinding.LayoutBannerMoreInfoDialogBinding
import com.example.sarycatalogtask.ui.dialogs.base.BaseDialogFragment
import com.example.sarycatalogtask.utils.extensions.doToast

class BannerMoreInfoDialog constructor(
    override val layoutRes: Int = R.layout.layout_banner_more_info_dialog
        ): BaseDialogFragment<LayoutBannerMoreInfoDialogBinding>() {


    companion object {
        const val TAG = "BannerMoreInfoDialog"
        const val ARG_KEY_BANNER_ITEM = "keyPassedBannerItem"
    }

    override fun initStuff() {
        isCancelable = true

        arguments?.apply {
            if (containsKey(ARG_KEY_BANNER_ITEM)){
                val bannerItem = getParcelable<BannerResult>(ARG_KEY_BANNER_ITEM)
                //doToast(bannerItem?.title ?: "")
                binding.obj = bannerItem
            }
        }

        binding.apply {

            btnOrderNow.setOnClickListener {

            }

        }



    }

}