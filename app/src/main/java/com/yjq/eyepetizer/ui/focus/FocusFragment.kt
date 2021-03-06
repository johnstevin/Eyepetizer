package com.yjq.eyepetizer.ui.focus

import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import com.yjq.eyepetizer.R
import com.yjq.eyepetizer.base.BaseFragment
import com.yjq.eyepetizer.bean.cards.Columns
import com.yjq.eyepetizer.bean.cards.Tab
import com.yjq.eyepetizer.ui.focus.mvp.FocusPresenter
import com.yjq.eyepetizer.ui.search.SearchFragment
import com.yjq.eyepetizer.util.rx.RxBaseObserver
import com.yjq.eyepetizer.util.rx.RxUtil
import kotlinx.android.synthetic.main.fragment_foucus.*

/**
 * 文件： FocusFragment
 * 描述：
 * 作者： YangJunQuan   2018-8-21.
 */
class FocusFragment : BaseFragment() {

    //data
    private var tabList = mutableListOf<Tab>()


    //other
    private lateinit var mPresenter: FocusPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = FocusPresenter(context!!)
    }

    override fun getLayoutResources(): Int {
        return R.layout.fragment_foucus
    }

    override fun initView() {
        initData()

        initToolbar()
    }

    private fun initData() {
        mPresenter.getFocusTabList()
                .compose(RxUtil.applySchedulers())
                .compose(bindToLifecycle())
                .subscribe(object : RxBaseObserver<Columns>(this) {
                    override fun onNext(t: Columns) {
                        tabList = t.tabInfo.tabList as MutableList<Tab>
                        initViewPager()
                        initTabLayout()
                    }
                })
    }


    private fun initToolbar() {
        tvTitle.typeface = Typeface.createFromAsset(context!!.assets, "fonts/Lobster-1.4.otf")
        ivSearch.setOnClickListener { SearchFragment().show(fragmentManager, "Subscription") }
    }


    private fun initViewPager() {
        with(viewPagerFocus) {
            adapter = object : FragmentPagerAdapter(childFragmentManager) {
                override fun getItem(position: Int): Fragment {
                    val apiUrl = tabList[position].apiUrl
                    return FocusTabFragment.newInstance(apiUrl)
                }

                override fun getCount(): Int {
                    return tabList.size
                }


                override fun getPageTitle(position: Int): CharSequence? {
                    return tabList[position].name
                }

            }
        }

    }


    private fun initTabLayout() {
        with(tabFocus) {
            setSelectedTabIndicatorHeight(0)
            tabGravity = TabLayout.GRAVITY_CENTER
            tabMode = TabLayout.MODE_FIXED
            setSelectedTabIndicatorColor(ContextCompat.getColor(context, R.color.dark))
            setupWithViewPager(viewPagerFocus)

        }
    }

    /**
     * ****************************************      RxJava 自定义回调处理    **********************************************
     */

    override fun onNetError() {
    }


    override fun onLoading(isLoad: Boolean) {
    }

}