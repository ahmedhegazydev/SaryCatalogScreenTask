package com.example.sarycatalogtask.ui.adapters

import com.example.sarycatalogtask.R
import com.example.sarycatalogtask.data.banners.BannerResult
import com.example.sarycatalogtask.data.catalog.CatalogResult
import com.example.sarycatalogtask.data.catalog.Data
import com.example.sarycatalogtask.ui.adapters.base.BaseAdapter


class CategoriesRecyclerAdapter : BaseAdapter<Data>() {

    private var data: List<Data> = emptyList()

    override fun getItemForPosition(position: Int): Data {
        return data[position]
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.category_list_item
    }

    override fun getItemCount(): Int {
        return data.size
    }


    fun setItemAndNotify(dataItems: MutableList<Data>){
        this.data = dataItems
        notifyDataSetChanged()
    }

    override val layoutResShimmer: Int
        get() = R.layout.category_list_item


}
