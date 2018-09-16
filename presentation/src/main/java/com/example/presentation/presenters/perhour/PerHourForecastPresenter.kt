package com.example.presentation.presenters.perhour

import com.example.presentation.mvp.BasePresenter
import javax.inject.Inject

class PerHourForecastPresenter @Inject constructor(): BasePresenter<IPerHourForecastContract.View>(), IPerHourForecastContract.Presenter