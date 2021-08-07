package com.example.sarycatalogtask.ui.adapters

import com.example.sarycatalogtask.R
import com.example.sarycatalogtask.data.banners.BannerResult
import com.example.sarycatalogtask.ui.adapters.base.BaseAdapter


class BannersRecyclerAdapter : BaseAdapter<BannerResult>() {

    private var data: List<BannerResult> = emptyList()

    override fun getItemForPosition(position: Int): BannerResult {
        return data[position]
    }

    override val layoutResShimmer: Int
        get() = R.layout.banners_list_item_shimmer

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.banners_list_item
    }

    override fun getItemCount(): Int {
        return data.size
    }


    fun setItemAndNotify(dataItems: MutableList<BannerResult>){
        this.data = dataItems
        notifyDataSetChanged()
    }

}
