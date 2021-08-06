package com.example.sarycatalogtask.ui.adapters.base;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.sarycatalogtask.R
import com.example.sarycatalogtask.BR


/**
 * @author Ahmed Hegzo
 * @param T as Model that extends a BaseModel
 * THis is a base RecyclerAdapter that assist every recyclerView adapter with this own abstract methods
 * with cooperation of DataBinding utils
 */
abstract class BaseAdapter<T> :
    RecyclerView.Adapter<BaseAdapter.MyViewHolder<T>>() {


    private var itemClickListener: OnItemClickListener<T>? = null
    private var lastPosition = -1

    open fun addOnItemClickHandler(itemClickListener: OnItemClickListener<T>) {
        this.itemClickListener = itemClickListener
    }

    interface OnItemClickListener<T> {
        fun onItemClick(item: T)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater, viewType, parent, false
        )

//        val height: Int = parent.measuredHeight / 4
//        binding.root.minimumHeight = height

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder<T>,
        position: Int
    ) {
        val item = getItemForPosition(position)
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(item)
        }
        holder.bind(item)

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

