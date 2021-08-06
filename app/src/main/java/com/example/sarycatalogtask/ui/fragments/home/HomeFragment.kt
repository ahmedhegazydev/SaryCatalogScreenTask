package com.example.sarycatalogtask.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sarycatalogtask.R
import com.example.sarycatalogtask.data.banners.BannerResult
import com.example.sarycatalogtask.databinding.FragmentHomeBinding
import com.example.sarycatalogtask.domain.states.State
import com.example.sarycatalogtask.ui.adapters.BannersRecyclerAdapter
import com.example.sarycatalogtask.ui.adapters.base.BaseAdapter
import com.example.sarycatalogtask.ui.dialogs.BannerMoreInfoDialog
import com.example.sarycatalogtask.ui.dialogs.BannerMoreInfoDialog.Companion.ARG_KEY_BANNER_ITEM
import com.example.sarycatalogtask.ui.fragments.base.BaseFragment
import com.example.sarycatalogtask.utils.extensions.doToast
import com.example.sarycatalogtask.viewmodel.CatalogsViewModel
import dagger.hilt.android.AndroidEntryPoint
import nl.qbusict.cupboard.annotation.Index
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment(override val layoutRes: Int = R.layout.fragment_home) :
    BaseFragment<FragmentHomeBinding>() {

    companion object {
        const val TAG = "__HomeFragment"
    }

    @Inject
    lateinit var adapterBanners: BannersRecyclerAdapter


    private val catalogCatalogsViewModel: CatalogsViewModel by viewModels()

    private fun FragmentHomeBinding.setupBannersObserver() {
        catalogCatalogsViewModel.uiStateGetAllBanners.observe(requireActivity()) { state ->
            when (state) {
                is State.Success -> {
                    state.data.let {
//                        it.result.map {
//                            Log.e(TAG, "setupBannersObserver: ${it.id}")
//                        }
                        it.result.toMutableList().apply {
                            repeat(5) { pos ->
                                add(this[pos].copy())
                            }
                            adapterBanners.setItemAndNotify(this)
                        }


                    }
                }
                is State.Error -> {
                    doToast(state.message)
                }
                is State.Loading -> {

                }
            }
        }
    }


    private fun setupCatalogsObserver() {
        catalogCatalogsViewModel.uiStateGetAllCatalogs.observe(requireActivity()) { state ->
            when (state) {
                is State.Success -> {
                    state.data.let {
                        it.result.map {
                            Log.e(TAG, "setupCatalogsObserver: ${it.id}")
                        }
                    }
                }
                is State.Error -> {
                    doToast(state.message)
                }
                is State.Loading -> {

                }
            }
        }
    }

    override fun initStuff() {

        binding?.apply {

            adapterBanners.addOnItemClickHandler(object :
                BaseAdapter.OnItemClickListener<BannerResult> {
                override fun onItemClick(item: BannerResult) {
                    val bannerMoreInfoDlg = BannerMoreInfoDialog()
                    bannerMoreInfoDlg.arguments = bundleOf(
                        ARG_KEY_BANNER_ITEM to item
                    )
                    bannerMoreInfoDlg.show(childFragmentManager, BannerMoreInfoDialog.TAG)
                }
            })
            rvBanners.adapter = adapterBanners



            catalogCatalogsViewModel.getAllBanners()
            setupBannersObserver()

            catalogCatalogsViewModel.getAllCatalogs()
            setupCatalogsObserver()

        }
    }


}