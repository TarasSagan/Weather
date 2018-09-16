package com.example.presentation.presenters.perhour

import com.example.presentation.mvp.IBasePresenter
import com.example.presentation.mvp.IBaseView

interface IPerHourForecastContract{
    interface View : IBaseView
    interface Presenter : IBasePresenter<IPerHourForecastContract.View>
}