package by.nalivajr.imagelistdemo.ui

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.paging.PagedList
import by.nalivajr.imagelistdemo.R
import by.nalivajr.imagelistdemo.domain.model.ImageInfo
import by.nalivajr.imagelistdemo.ui.adapter.ImagesPagedAdapter
import by.nalivajr.imagelistdemo.ui.viewmodel.PagedImagesListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class InfiniteImagesPagingActivity : BaseImagesCollectionActivity<PagedList<ImageInfo>>() {

    override val imagesAdapter: ImagesPagedAdapter by lazy { ImagesPagedAdapter() }

    override val viewModel: PagedImagesListViewModel by viewModel()

    override fun onDataLoaded(data: PagedList<ImageInfo>?) {
        imagesAdapter.submitList(data)
    }

    override fun onReload() {
        viewModel.loadImages()
    }

    override fun onAddImage() {
        Toast.makeText(this, R.string.not_supported_for_infinete, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, InfiniteImagesPagingActivity::class.java))
        }
    }
}