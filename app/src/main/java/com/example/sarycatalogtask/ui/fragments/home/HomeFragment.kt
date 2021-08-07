package com.example.sarycatalogtask.ui.fragments.home

import android.graphics.Rect
import android.view.View
import android.widget.ScrollView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sarycatalogtask.R
import com.example.sarycatalogtask.data.banners.BannerResult
import com.example.sarycatalogtask.data.banners.BannersData
import com.example.sarycatalogtask.data.catalog.CatalogsData
import com.example.sarycatalogtask.data.catalog.Data
import com.example.sarycatalogtask.databinding.FragmentHomeBinding
import com.example.sarycatalogtask.domain.states.State
import com.example.sarycatalogtask.ui.adapters.*
import com.example.sarycatalogtask.ui.adapters.base.BaseAdapter
import com.example.sarycatalogtask.ui.dialogs.BannerMoreInfoDialog
import com.example.sarycatalogtask.ui.dialogs.BannerMoreInfoDialog.Companion.ARG_KEY_BANNER_ITEM
import com.example.sarycatalogtask.ui.fragments.base.BaseFragment
import com.example.sarycatalogtask.utils.extensions.*
import com.example.sarycatalogtask.viewmodel.CatalogsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment(override val layoutRes: Int = R.layout.fragment_home) :
    BaseFragment<FragmentHomeBinding>() {

    companion object {
        const val TAG = "__HomeFragment"
    }

    @Inject
    lateinit var adapterTopTopBanners: TopBannersRecyclerAdapter

    @Inject
    lateinit var adapterTopTrending: TopTrendingRecyclerAdapter

    @Inject
    lateinit var adapterCategories: CategoriesRecyclerAdapter

    @Inject
    lateinit var adapterBottomCenterBanners: CenterBottomBannersAdapter


    @Inject
    lateinit var adapterBusinessType: BusinessTypesRecyclerAdapter


    private val catalogCatalogsViewModel: CatalogsViewModel by viewModels()

    private fun FragmentHomeBinding.setupBannersObserver() {
        catalogCatalogsViewModel.uiStateGetAllBanners.observe(requireActivity()) { state ->
            when (state) {
                is State.Success -> {
                    state.data.let {

                        adapterTopTopBanners.setShimmerEnabled(false)
                        adapterTopTopBanners.setItemAndNotify(it.result.toMutableList())

                    }
                }
                is State.Error -> {
                    doToast(state.message)
                }
                is State.Loading -> {
                    adapterTopTopBanners.setShimmerEnabled(true)
                    adapterTopTopBanners.setItemAndNotify(BannersData.populateDShimmerDataList())
                }
            }
        }
    }

    private lateinit var catalogsData: CatalogsData

    private fun FragmentHomeBinding.setupCatalogsObserver() {
        catalogCatalogsViewModel.uiStateGetAllCatalogs.observe(requireActivity()) { state ->
            when (state) {
                is State.Success -> {
                    state.data.let { it ->

                        this@HomeFragment.catalogsData = it

                        adapterTopTrending.setShimmerEnabled(false)
                        adapterCategories.setShimmerEnabled(false)
                        adapterBottomCenterBanners.setShimmerEnabled(false)
                        adapterBusinessType.setShimmerEnabled(false)


                        //Top and Trending
                        notifyTopAndTrending(it)


                        ///By Categories
                        notifyByCategiriesFilter(it)


                        ///Center Bottom Banners
//                        lifecycleScope.launchWhenCreated {
//                            delay(2000)
//                            notifyCenterBottomBanners(it)
//                            rvCenterBanners.expand()
//                        }


//                        ///By Business Types
                        notifyByBusinessTypesFilters(it)


                    }
                }
                is State.Error -> {
                    doToast(state.message)
                }
                is State.Loading -> {
//                    showLoaderShimmer()
                }
            }
        }
    }

    private fun notifyByBusinessTypesFilters(it: CatalogsData) {
        it.result.filter {
            it.id == 13
        }.let {
            adapterBusinessType.setItemAndNotify(it.first().data as MutableList<Data>)
        }
    }

    private fun notifyCenterBottomBanners(it: CatalogsData) {
        it.result.filter {
            it.id == 148
        }.let {
            adapterBottomCenterBanners.setItemAndNotify(it.first().data as MutableList<Data>)
        }
    }

    private fun notifyByCategiriesFilter(it: CatalogsData) {
        it.result.filter {
            it.id == 147
        }.let {
            adapterCategories.setItemAndNotify(it.first().data as MutableList<Data>)
        }
    }

    private fun notifyTopAndTrending(it: CatalogsData) {
        it.result.filter {
            it.id == 3
        }.let {
            adapterTopTrending.setItemAndNotify(it.first().data as MutableList<Data>)
        }
    }

    private var isCenterBottomBannersShows = true

    override fun initStuff() {
        binding?.apply {

//            rvTopTrending.hide()
//            rvTopBanners.hide()
            rvCenterBanners.hide()

            scrollView.viewTreeObserver.addOnScrollChangedListener {
                val scrollY: Int = scrollView.scrollY // For ScrollView
                val scrollX: Int = scrollView.scrollX // For HorizontalScrollView
                if (isViewVisible(scrollView, rvBusinessTypes) && isCenterBottomBannersShows){
                    if (::catalogsData.isInitialized){
                            notifyCenterBottomBanners(catalogsData)
                            rvCenterBanners.expand()
                        isCenterBottomBannersShows = !isCenterBottomBannersShows
                    }
                }
            }

            //Top Banners
            handlingAdapterTopBanners()

            ////Top and Trending
            handlingAdapterTopAndTrending()


            ///Categories
            handlingAdapterByCategories()


            ///Center  Bottom Banners
            handlingAdapterCenterBottomBanners()


            ///By Business Type
            handlingAdapterByBusinessTypeFilter()


            catalogCatalogsViewModel.getAllBanners()
            setupBannersObserver()

            catalogCatalogsViewModel.getAllCatalogs()
            setupCatalogsObserver()


            showLoaderShimmer()
        }
    }


    private fun isViewVisible(scrollView: ScrollView, view: View): Boolean {
        val scrollBounds = Rect()
        scrollView.getDrawingRect(scrollBounds)
        val top: Float = view.y
        val bottom: Float = top + view.height
        return scrollBounds.top < top && scrollBounds.bottom > bottom
    }

    private fun FragmentHomeBinding.handlingAdapterByBusinessTypeFilter() {
        adapterBusinessType.addOnItemClickHandler(object :
            BaseAdapter.OnItemClickListener<Data> {
            override fun onItemClick(item: Data) {
                doToast(item.name ?: "")
            }
        })
        rvBusinessTypes.adapter = adapterBusinessType
        rvBusinessTypes.addViewTreeObserver(
            resources.getDimension(R.dimen.dimen_business_type_width).toInt()
        )
    }

    private fun FragmentHomeBinding.handlingAdapterCenterBottomBanners() {
        adapterBottomCenterBanners.addOnItemClickHandler(object :
            BaseAdapter.OnItemClickListener<Data> {
            override fun onItemClick(item: Data) {
                doToast(item.name ?: "")
            }
        })
        rvCenterBanners.adapter = adapterBottomCenterBanners
    }

    private fun FragmentHomeBinding.handlingAdapterByCategories() {
        adapterCategories.addOnItemClickHandler(object :
            BaseAdapter.OnItemClickListener<Data> {
            override fun onItemClick(item: Data) {
                doToast(item.name ?: "")
            }
        })
        rvCategories.adapter = adapterCategories
        rvCategories.addViewTreeObserver(
            resources.getDimension(R.dimen.dimen_category_width).toInt()
        )
    }

    private fun FragmentHomeBinding.handlingAdapterTopAndTrending() {
        adapterTopTrending.addOnItemClickHandler(object :
            BaseAdapter.OnItemClickListener<Data> {
            override fun onItemClick(item: Data) {
                doToast(item.name ?: "")
            }
        })
        rvTopTrending.adapter = adapterTopTrending
    }

    private fun FragmentHomeBinding.handlingAdapterTopBanners() {
        adapterTopTopBanners.addOnItemClickHandler(object :
            BaseAdapter.OnItemClickListener<BannerResult> {
            override fun onItemClick(item: BannerResult) {
                val bannerMoreInfoDlg = BannerMoreInfoDialog()
                bannerMoreInfoDlg.arguments = bundleOf(
                    ARG_KEY_BANNER_ITEM to item
                )
                bannerMoreInfoDlg.show(childFragmentManager, BannerMoreInfoDialog.TAG)
            }
        })
        rvTopBanners.adapter = adapterTopTopBanners
    }

    private fun showLoaderShimmer() {
        adapterTopTrending.setShimmerEnabled(true)
        adapterTopTrending.setItemAndNotify(Data.populateShimmerDataList())


        adapterCategories.setShimmerEnabled(true)
        adapterCategories.setItemAndNotify(Data.populateShimmerDataList())

        adapterBottomCenterBanners.setShimmerEnabled(true)
        adapterBottomCenterBanners.setItemAndNotify(Data.populateShimmerDataList())

        adapterBusinessType.setShimmerEnabled(true)
        adapterBusinessType.setItemAndNotify(Data.populateShimmerDataList())
    }


}