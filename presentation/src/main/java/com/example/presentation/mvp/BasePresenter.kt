package com.example.presentation.mvp

import com.example.presentation.IRouter
import javax.inject.Inject

abstract class BasePresenter<View: IBaseView>: IBasePresenter<View> {
    @Inject internal lateinit var router: IRouter
    override var mView: View? = null
}