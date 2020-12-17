package by.nalivajr.imagelistdemo.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.nalivajr.imagelistdemo.R
import by.nalivajr.imagelistdemo.databinding.ActivityImagesCollectionBinding
import by.nalivajr.imagelistdemo.domain.model.ImagesPage
import by.nalivajr.imagelistdemo.ui.adapter.ImagesPageAdapter
import by.nalivajr.imagelistdemo.ui.viewmodel.ImagesListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ImagesPagesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImagesCollectionBinding

    private val pagesAdapter: ImagesPageAdapter by lazy { ImagesPageAdapter(supportFragmentManager) }
    private val viewModel: ImagesListViewModel by viewModel()
    private var scrollNextPage: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagesCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initList()
        initSwipeLayout()

        observeData()

        viewModel.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.onSaveState(outState)
    }

    private fun initSwipeLayout() {
        binding.listContainer.isEnabled = false
    }

    private fun initList() {
        with(binding.imagesPages) {
            adapter = pagesAdapter
        }
    }

    private fun observeData() {
        viewModel.imagesList.observe(this) {
            it.onSuccess(::onDataLoaded)
                .onError { msg, _ ->
                    Toast.makeText(this@ImagesPagesActivity, msg, Toast.LENGTH_SHORT).show()
                }
        }

        viewModel.loadingState.observe(this) {
            binding.listContainer.isRefreshing = it
        }
    }

    private fun onDataLoaded(data: List<ImagesPage>?) {
        val pages = data ?: emptyList()
        pagesAdapter.pages = pages
        val openPageIndex = if (scrollNextPage) pages.size else 0
        binding.imagesPages.post { binding.imagesPages.setCurrentItem(openPageIndex, true) }
        scrollNextPage = false
    }

    private fun onReload() {
        viewModel.reloadImages()
    }

    private fun onAddImage() {
        scrollNextPage = true
        viewModel.addImage()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_control_images, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_add -> {
                onAddImage()
                true
            }
            R.id.item_reload -> {
                onReload()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}