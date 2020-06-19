package com.hdsx.x1.ui.main.discovery

import android.content.Context
import android.widget.ImageView
import com.hdsx.R
import com.hdsx.x1.model.bean.Banner
import com.hdsx.x1.util.core.ImageOptions
import com.hdsx.x1.util.core.loadImage
import com.youth.banner.loader.ImageLoader

/**
 * 广告栏
 */
class BannerImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        val imagePath = (path as? Banner)?.imagePath
        imageView?.loadImage(imagePath, ImageOptions().apply {
            placeholder = R.drawable.shape_bg_image_default
            error = R.drawable.shape_bg_image_default
        })
    }

}