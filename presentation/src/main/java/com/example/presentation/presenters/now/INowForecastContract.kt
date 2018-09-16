package com.example.presentation.presenters.now

import com.example.presentation.mvp.IBasePresenter
import com.example.presentation.mvp.IBaseView

interface INowForecastContract {
    interface View: IBaseView
    interface Presenter: IBasePresenter<INowForecastContract.View>
}