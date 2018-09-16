package com.example.presentation.presenters.today

import com.example.presentation.mvp.BasePresenter
import javax.inject.Inject

class TodayForecastPresenter @Inject constructor(): BasePresenter<ITodayForecastContract.View>(), ITodayForecastContract.Presenter