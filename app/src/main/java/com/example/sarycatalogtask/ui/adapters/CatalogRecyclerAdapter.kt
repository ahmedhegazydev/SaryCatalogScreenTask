package com.example.sarycatalogtask.ui.adapters

import com.example.sarycatalogtask.R
import com.example.sarycatalogtask.data.banners.BannerResult
import com.example.sarycatalogtask.data.catalog.CatalogResult
import com.example.sarycatalogtask.ui.adapters.base.BaseAdapter


class CatalogRecyclerAdapter : BaseAdapter<CatalogResult>() {

    private var data: List<CatalogResult> = emptyList()

    override fun getItemForPosition(position: Int): CatalogResult {
        return data[position]
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.banners_list_item
    }

    override fun getItemCount(): Int {
        return data.size
    }


    fun setItemAndNotify(dataItems: List<CatalogResult>){
        this.data = dataItems
        notifyDataSetChanged()
    }

}
