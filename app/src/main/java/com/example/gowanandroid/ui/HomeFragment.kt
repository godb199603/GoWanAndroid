package com.example.gowanandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gowanandroid.R
import com.example.gowanandroid.ui.home.*
import com.example.gowanandroid.ui.home.popular.PopularFragment
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var fragments: List<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragments = listOf(
            LatestFragment.newInstance(),
            PopularFragment.newInstance(),
            PlazaFragment.newInstance(),
            ProjectFragment.newInstance(),
            WechatFragment.newInstance()
        )
        val titles = listOf<CharSequence>(
            getString(R.string.popular_articles),
            getString(R.string.latest_project),
            getString(R.string.plaza),
            getString(R.string.project_category),
            getString(R.string.wechat_public)
        )
        viewPager.adapter = SimpleFragmentPagerAdapter(childFragmentManager, fragments, titles)
        viewPager.offscreenPageLimit = fragments.size
        tabLayout.setupWithViewPager(viewPager)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
