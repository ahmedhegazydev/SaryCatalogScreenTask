package com.example.sarycatalogtask.utils.binding

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
//import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import org.sufficientlysecure.htmltextview.HtmlResImageGetter
import org.sufficientlysecure.htmltextview.HtmlTextView

object BindingUtils {


    @BindingAdapter("app:set_text_html")
    @JvmStatic
    fun viewHtmlText(view: HtmlTextView?, text: String?) {
        view?.apply {
            context?.let {
                setHtml(text ?: "", HtmlResImageGetter(it))
            }
        }
    }

    @BindingAdapter("android:glideUri")
    @JvmStatic
    fun glideLoader(view: ImageView, @NonNull uri: String?) {
//        val multi = MultiTransformation<Bitmap>(
//            RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT),
//            RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT),
//            RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.TOP_LEFT),
//            RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT)
//        )

        Glide.with(view.context)
            .load(uri)
//            .apply(RequestOptions().transform(CenterCrop(), multi))
//            .apply(RequestOptions().transform(CenterCrop()))
            .into(view)
    }

}