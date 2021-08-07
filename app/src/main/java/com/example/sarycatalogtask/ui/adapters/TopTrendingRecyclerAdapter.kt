package com.example.sarycatalogtask.ui.adapters

import com.example.sarycatalogtask.R
import com.example.sarycatalogtask.data.banners.BannerResult
import com.example.sarycatalogtask.data.catalog.CatalogResult
import com.example.sarycatalogtask.data.catalog.Data
import com.example.sarycatalogtask.ui.adapters.base.BaseAdapter


class TopTrendingRecyclerAdapter : BaseAdapter<Data>() {

    private var data: List<Data> = emptyList()

    override fun getItemForPosition(position: Int): Data {
        return data[position]
    }
    override val layoutResShimmer: Int
        get() = R.layout.shimmer_item_top_trending


    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.list_item_top_and_trending
    }

    override fun getItemCount(): Int {
        return data.size
    }


    fun setItemAndNotify(dataItems: MutableList<Data>){
        this.data = dataItems
        notifyDataSetChanged()
    }

}
