package by.nalivajr.imagelistdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.nalivajr.imagelistdemo.databinding.ActivityDemoSelectionBinding

class DemoSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDemoSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.continuesDemo.setOnClickListener {
            InfiniteImagesPagingActivity.start(this)
        }

        binding.fixedDemo.setOnClickListener {
            LimitedImagesListActivity.start(this)
        }
    }
}