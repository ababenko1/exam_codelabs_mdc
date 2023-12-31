package com.google.codelabs.mdc.kotlin.shrine

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.codelabs.mdc.kotlin.shrine.network.ProductEntry
import com.google.codelabs.mdc.kotlin.shrine.staggeredgridlayout.StaggeredProductCardRecyclerViewAdapter
import kotlinx.android.synthetic.main.shr_product_grid_fragment.view.app_bar
import kotlinx.android.synthetic.main.shr_product_grid_fragment.view.product_grid
import kotlinx.android.synthetic.main.shr_product_grid_fragment.view.recycler_view

class ProductGridFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.shr_product_grid_fragment, container, false)
        (activity as MainActivity).setSupportActionBar(view.app_bar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.product_grid.background = ContextCompat.getDrawable(requireContext(), R.drawable.shr_product_grid_background_shape)
        }

        view.app_bar.setNavigationOnClickListener(NavigationIconClickListener(
            requireActivity(), view.product_grid,
            AccelerateDecelerateInterpolator(),
        ContextCompat.getDrawable(requireContext(), R.drawable.shr_branded_menu),
        ContextCompat.getDrawable(requireContext(), R.drawable.shr_close_menu)))

        initList(view)
        return view
    }

    private fun initList(view: View) {
        view.recycler_view.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(context, 2, RecyclerView.HORIZONTAL, false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 2) 2 else 1
            }
        }
        view.recycler_view.layoutManager = gridLayoutManager
        val adapter = StaggeredProductCardRecyclerViewAdapter(ProductEntry.initProductEntryList(resources))
        view.recycler_view.adapter = adapter
        val largePadding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small)
        val itemDecoration = ProductGridItemDecoration(largePadding, smallPadding)
        view.recycler_view.addItemDecoration(itemDecoration)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shr_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
