package by.nalivajr.imagelistdemo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import by.nalivajr.imagelistdemo.databinding.FragmentImagesGridBinding
import by.nalivajr.imagelistdemo.domain.model.ImageInfo
import by.nalivajr.imagelistdemo.ui.adapter.SimpleImagesAdapter
import by.nalivajr.imagelistdemo.ui.tools.ItemDecoration

class ImagesGridFragment : Fragment() {

    private lateinit var binding: FragmentImagesGridBinding
    private val imagesAdapter: SimpleImagesAdapter by lazy { SimpleImagesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagesGridBinding.inflate(layoutInflater)
        val images = requireArguments().getParcelableArrayList<ImageInfo>(ARG_IMAGES) ?: emptyList()
        initList(images)
        return binding.root
    }
    
    private fun initList(images: List<ImageInfo>) {
        with(binding.imagesList) {
            layoutManager = GridLayoutManager(requireActivity(), SPAN_COUNT, GridLayoutManager.VERTICAL, false)
            adapter = imagesAdapter
            addItemDecoration(ItemDecoration(requireActivity()))
            
            imagesAdapter.submitList(images)
        }
    }
    
    companion object {
        private const val SPAN_COUNT = 7
        private const val ARG_IMAGES = "arg_images"

        fun newInstance(images: List<ImageInfo>): Fragment {
            val args = Bundle()
            args.putParcelableArrayList(ARG_IMAGES, ArrayList(images))

            val fragment = ImagesGridFragment()
            fragment.arguments = args
            return fragment
        }
    }
}