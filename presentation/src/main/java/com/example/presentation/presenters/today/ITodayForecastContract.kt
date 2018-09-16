package com.example.presentation.presenters.today

import com.example.presentation.mvp.IBasePresenter
import com.example.presentation.mvp.IBaseView

interface ITodayForecastContract{
    interface View: IBaseView
    interface Presenter: IBasePresenter<ITodayForecastContract.View>
}