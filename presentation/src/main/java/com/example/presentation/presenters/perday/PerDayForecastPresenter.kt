package com.example.presentation.presenters.perday

import com.example.presentation.mvp.BasePresenter
import javax.inject.Inject

class PerDayForecastPresenter @Inject constructor(): BasePresenter<IPerDayForecastContract.View>(), IPerDayForecastContract.Presenter