package by.nalivajr.imagelistdemo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import by.nalivajr.imagelistdemo.domain.model.ImageInfo
import by.nalivajr.imagelistdemo.ui.adapter.SimpleImagesAdapter
import by.nalivajr.imagelistdemo.ui.viewmodel.ImagesListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LimitedImagesListActivity : BaseImagesCollectionActivity<List<ImageInfo>>() {

    override val imagesAdapter by lazy { SimpleImagesAdapter() }

    override val viewModel: ImagesListViewModel by viewModel()

    var scrollEndFlag = false

    override fun onDataLoaded(data: List<ImageInfo>?) {
        imagesAdapter.submitList(data)
        if (scrollEndFlag) {
            with(binding.imagesList) {
                post { smoothScrollToPosition(data!!.size - 1) }
            }
        }
    }

    override fun onReload() {
        viewModel.reloadImages()
    }

    override fun onAddImage() {
        scrollEndFlag = true
        viewModel.addImage()
    }


    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LimitedImagesListActivity::class.java))
        }
    }
}