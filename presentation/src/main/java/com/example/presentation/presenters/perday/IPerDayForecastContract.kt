package com.example.presentation.presenters.perday

import com.example.presentation.mvp.IBasePresenter
import com.example.presentation.mvp.IBaseView

interface IPerDayForecastContract {
    interface View: IBaseView
    interface Presenter: IBasePresenter<IPerDayForecastContract.View>
}