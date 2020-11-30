package by.nalivajr.imagelistdemo.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import by.nalivajr.imagelistdemo.domain.model.ImageInfo
import by.nalivajr.imagelistdemo.ui.widget.RoundedSquareImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ImageInfoHolder(private val squareImageView: RoundedSquareImageView)
    : RecyclerView.ViewHolder(squareImageView) {

    fun bind(imageInfo: ImageInfo) {
        squareImageView.loading = true
        Picasso.get()
            .load(imageInfo.url)
            .into(squareImageView.innerImageView, object: Callback {
                override fun onSuccess() {
                    squareImageView.loading = false
                }

                override fun onError(e: Exception?) {
                    squareImageView.loading = false
                }
            })
    }
}