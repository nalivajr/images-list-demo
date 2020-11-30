package by.nalivajr.imagelistdemo.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import by.nalivajr.imagelistdemo.domain.model.ImageInfo

class ImagesInfoDiffer : DiffUtil.ItemCallback<ImageInfo>() {
    override fun areItemsTheSame(oldItem: ImageInfo, newItem: ImageInfo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ImageInfo, newItem: ImageInfo): Boolean {
        return oldItem.url == newItem.url
    }
}