package com.example.sarycatalogtask.ui.fragments.home

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.sarycatalogtask.R
import com.example.sarycatalogtask.data.banners.BannerResult
import com.example.sarycatalogtask.data.banners.BannersData
import com.example.sarycatalogtask.data.catalog.Data
import com.example.sarycatalogtask.databinding.FragmentHomeBinding
import com.example.sarycatalogtask.domain.states.State
import com.example.sarycatalogtask.ui.adapters.*
import com.example.sarycatalogtask.ui.adapters.base.BaseAdapter
import com.example.sarycatalogtask.ui.dialogs.BannerMoreInfoDialog
import com.example.sarycatalogtask.ui.dialogs.BannerMoreInfoDialog.Companion.ARG_KEY_BANNER_ITEM
import com.example.sarycatalogtask.ui.fragments.base.BaseFragment
import com.example.sarycatalogtask.utils.extensions.addViewTreeObserver
import com.example.sarycatalogtask.utils.extensions.doToast
import com.example.sarycatalogtask.viewmodel.CatalogsViewModel
import dagger.hilt.android.AndroidEntryPoint
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
//                        it.result.map {
//                            Log.e(TAG, "setupBannersObserver: ${it.id}")
//                        }
//                        it.result.toMutableList().apply {
//                            repeat(5) { pos ->
//                                add(this[pos].copy())
//                            }
//                            adapterBanners.setItemAndNotify(this)
//                        }

                        adapterTopTopBanners.setShimmerEnabled(false)
                        adapterTopTopBanners.setItemAndNotify(it.result.toMutableList())
//                        shimmerTopBanners.hide()
//                        rvBanners.show()

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


    private fun FragmentHomeBinding.setupCatalogsObserver() {
        catalogCatalogsViewModel.uiStateGetAllCatalogs.observe(requireActivity()) { state ->
            when (state) {
                is State.Success -> {
                    state.data.let { it ->

                        adapterTopTrending.setShimmerEnabled(false)
                        adapterCategories.setShimmerEnabled(false)
                        adapterBottomCenterBanners.setShimmerEnabled(false)
                        adapterBusinessType.setShimmerEnabled(false)

//                        it.result.map {
//                            Log.e(TAG, "setupCatalogsObserver: ${it.id}")
//                        }

                        //Top and Trending
                        it.result.filter {
                            it.id == 3
                        }.let {
                            adapterTopTrending.setItemAndNotify(it.first().data as MutableList<Data>)
                        }
//                        shimmerTopTrending.hide()
//                        rvTopTrending.show()


                        ///By Categories
                        it.result.filter {
                            it.id == 147
                        }.let {
                            adapterCategories.setItemAndNotify(it.first().data as MutableList<Data>)
                        }


                        ///Center Bottom Banners
                        it.result.filter {
                            it.id == 148
                        }.let {
                            adapterBottomCenterBanners.setItemAndNotify(it.first().data as MutableList<Data>)
                        }

//                        ///By Business Types
                        it.result.filter {
                            it.id == 13
                        }.let {
                            adapterBusinessType.setItemAndNotify(it.first().data as MutableList<Data>)
                        }


                    }
                }
                is State.Error -> {
                    doToast(state.message)
                }
                is State.Loading -> {
//                    adapterTopTrending.setShimmerEnabled(true)
//                    adapterTopTrending.setItemAndNotify(Data.populateShimmerDataList())
//
//
//                    adapterCategories.setShimmerEnabled(true)
//                    adapterCategories.setItemAndNotify(Data.populateShimmerDataList())
//
//                    adapterBottomCenterBanners.setShimmerEnabled(true)
//                    adapterBottomCenterBanners.setItemAndNotify(Data.populateShimmerDataList())
//
//                    adapterBusinessType.setShimmerEnabled(true)
//                    adapterBusinessType.setItemAndNotify(Data.populateShimmerDataList())
                }
            }
        }
    }

    override fun initStuff() {
        binding?.apply {


            //Top Banners
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
            rvBanners.adapter = adapterTopTopBanners
//            adapterBanners.setShimmerEnabled(true)
//            adapterBanners.setItemAndNotify(BannersData.populateDShimmerDataList())


            ////Top and Trending
            adapterTopTrending.addOnItemClickHandler(object :
                BaseAdapter.OnItemClickListener<Data> {
                override fun onItemClick(item: Data) {
                    doToast(item.name ?: "")
                }
            })
            rvTopTrending.adapter = adapterTopTrending



            ///Categories
            adapterCategories.addOnItemClickHandler(object :
                BaseAdapter.OnItemClickListener<Data> {
                override fun onItemClick(item: Data) {
                    doToast(item.name ?: "")
                }
            })
            rvCategories.adapter = adapterCategories
//            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
//            rvCategories.addItemDecoration(SpacesItemDecoration(spacingInPixels))
            /*val gridLayoutManager = GridLayoutManager(requireActivity(), 4)
            rvCategories.layoutManager = gridLayoutManager
            rvCategories.viewTreeObserver.addOnGlobalLayoutListener(
                object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        rvCategories.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        val viewWidth: Int = rvCategories.measuredWidth
                        val cardViewWidth =
                            activity!!.resources.getDimension(R.dimen.dimen_category_width)
                        val newSpanCount =
                            floor((viewWidth / cardViewWidth).toDouble()).toInt()
                        gridLayoutManager.spanCount = newSpanCount
                        gridLayoutManager.requestLayout()
                    }
                })*/
            rvCategories.addViewTreeObserver(resources.getDimension(R.dimen.dimen_category_width).toInt())


            ///Center  Bottom Banners
            adapterBottomCenterBanners.addOnItemClickHandler(object :
                BaseAdapter.OnItemClickListener<Data> {
                override fun onItemClick(item: Data) {
                    doToast(item.name ?: "")
                }
            })
            rvCenterBanners.adapter = adapterBottomCenterBanners


            ///By Business Type
            adapterBusinessType.addOnItemClickHandler(object :
                BaseAdapter.OnItemClickListener<Data> {
                override fun onItemClick(item: Data) {
                    doToast(item.name ?: "")
                }
            })
            rvBusinessTypes.adapter = adapterBusinessType
            rvBusinessTypes.addViewTreeObserver(resources.getDimension(R.dimen.dimen_business_type_width).toInt())



            catalogCatalogsViewModel.getAllBanners()
            setupBannersObserver()

            catalogCatalogsViewModel.getAllCatalogs()
            setupCatalogsObserver()




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


}