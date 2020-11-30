package by.nalivajr.imagelistdemo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import by.nalivajr.imagelistdemo.databinding.ItemListImageBinding
import by.nalivajr.imagelistdemo.domain.model.ImageInfo

class ImagesPagedAdapter : PagedListAdapter<ImageInfo, ImageInfoHolder>(ImagesInfoDiffer()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageInfoHolder {
        val itemBinding = ItemListImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageInfoHolder(itemBinding.root)
    }

    override fun onBindViewHolder(holder: ImageInfoHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}