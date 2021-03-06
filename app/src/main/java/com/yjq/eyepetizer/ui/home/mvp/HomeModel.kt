package com.yjq.eyepetizer.ui.home.mvp

import android.content.Context
import com.yjq.eyepetizer.bean.cards.ColumnPage
import com.yjq.eyepetizer.bean.cards.Columns
import com.yjq.eyepetizer.manager.RetrofitManager
import io.reactivex.Observable

/**
 * 文件： HomeModel
 * 描述：
 * 作者： YangJunQuan   2018-8-17.
 */
class HomeModel : HomeContract.Model {

    override fun getColumns(): Observable<Columns> {
        return RetrofitManager.EyeAPI().getColumnList()
    }

    fun getColumnPage(url: String): Observable<ColumnPage> {
        return RetrofitManager.EyeAPI().getColumnHomePage(url)
    }

}