package com.example.sarycatalogtask.ui.adapters.base;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.sarycatalogtask.BR
import com.example.sarycatalogtask.utils.extensions.hide
import com.example.sarycatalogtask.utils.extensions.show


/**
 * @author Ahmed Hegzo
 * @param T as Model that extends a BaseModel
 * THis is a base RecyclerAdapter that assist every recyclerView adapter with this own abstract methods
 * with cooperation of DataBinding utils
 */
abstract class BaseAdapter<T> :
    RecyclerView.Adapter<BaseAdapter.MyViewHolder<T>>() {


    @get:LayoutRes
    protected abstract val layoutResShimmer: Int

    private var itemClickListener: OnItemClickListener<T>? = null
    private var lastPosition = -1

    open fun addOnItemClickHandler(itemClickListener: OnItemClickListener<T>) {
        this.itemClickListener = itemClickListener
    }

    private var isShimmerEnabled = false

    open fun setShimmerEnabled(isShimmerEnabled: Boolean) {
        this.isShimmerEnabled = isShimmerEnabled
    }

    interface OnItemClickListener<T> {
        fun onItemClick(item: T)
    }

    init {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding: ViewDataBinding = if (isShimmerEnabled){
//            DataBindingUtil.inflate(layoutInflater, layoutResShimmer, parent, false)
//        }else{
//            DataBindingUtil.inflate(
//                layoutInflater, viewType, parent, false
//            )
//        }
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder<T>,
        position: Int
    ) {

        val layoutInflater = LayoutInflater.from(holder.itemView.context)


        if (isShimmerEnabled) {
            if (holder.itemView.findViewWithTag<ViewGroup>("shimmer") == null) {
                (holder.itemView as ViewGroup).apply {
                    val shimmer = DataBindingUtil.inflate<ViewDataBinding>(
                        layoutInflater,
                        layoutResShimmer,
                        null,
                        false
                    )
                    holder.itemView.children.forEach {
                        it.hide()
                    }
                    shimmer.root.tag = "shimmer"
                    addView(
                        shimmer.root, ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )
                    )

                }
            }
        } else {
            val item = getItemForPosition(position)
            if (holder.itemView.findViewWithTag<ViewGroup>("shimmer") != null) {
                (holder.itemView as ViewGroup).apply {
                    removeView(holder.itemView.findViewWithTag<ViewGroup>("shimmer"))
                }
                holder.itemView.children.forEach {
                    it.show()
                }
            }

            holder.itemView.setOnClickListener {
                itemClickListener?.onItemClick(item)
            }
            holder.bind(item)
        }

//        setAnimation(holder.itemView, position);

    }


    /**
     * Here is the key method to apply the animation
     */
    open fun setAnimation(viewToAnimate: View, position: Int) {
//        if (position > lastPosition) {
//            val animation: Animation = AnimationUtils.loadAnimation(
//                viewToAnimate.context,
////                R.anim.fade_in
//            )
//            viewToAnimate.startAnimation(animation)
//            lastPosition = position
//        }
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    protected abstract fun getItemForPosition(position: Int): T
    protected abstract fun getLayoutIdForPosition(position: Int): Int


    class MyViewHolder<T>(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(item: T) {
            binding.setVariable(BR.obj, item)
            binding.executePendingBindings()
        }

        fun clearAnimation() {
            binding.root.clearAnimation()
        }
    }


    override fun onViewDetachedFromWindow(holder: MyViewHolder<T>) {
        (holder as MyViewHolder<*>).clearAnimation()
    }
}

