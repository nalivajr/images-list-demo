package by.nalivajr.imagelistdemo.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.nalivajr.imagelistdemo.R
import by.nalivajr.imagelistdemo.databinding.ActivityImagesCollectionBinding
import by.nalivajr.imagelistdemo.ui.tools.ItemDecoration
import by.nalivajr.imagelistdemo.ui.viewmodel.BaseImagesListViewModel

abstract class BaseImagesCollectionActivity<T> : AppCompatActivity() {

    protected lateinit var binding: ActivityImagesCollectionBinding

    protected abstract val imagesAdapter: RecyclerView.Adapter<*>
    protected abstract val viewModel: BaseImagesListViewModel<T>

    protected abstract fun onReload()

    protected abstract fun onAddImage()

    protected abstract fun onDataLoaded(data: T?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagesCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initList()
        initSwipeLayout()

        observeData()

        if (savedInstanceState == null) {
            onReload()
        }
    }

    private fun initSwipeLayout() {
        binding.listContainer.isEnabled = false
    }

    private fun initList() {
        with(binding.imagesList) {
            layoutManager = GridLayoutManager(this@BaseImagesCollectionActivity, SPAN_COUNT, GridLayoutManager.HORIZONTAL, false)
            adapter = imagesAdapter
            addItemDecoration(ItemDecoration(this@BaseImagesCollectionActivity))
        }
    }

    private fun observeData() {
        viewModel.imagesList.observe(this) {
            it.onSuccess(::onDataLoaded)
                .onError { msg, _ ->
                    Toast.makeText(this@BaseImagesCollectionActivity, msg, Toast.LENGTH_SHORT).show()
                }
        }

        viewModel.loadingState.observe(this) {
            binding.listContainer.isRefreshing = it
        }
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

    companion object {
        const val SPAN_COUNT = 10
    }
}