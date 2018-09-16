package com.example.presentation.presenters.now

import com.example.presentation.mvp.BasePresenter
import javax.inject.Inject

class NowForecastPresenter @Inject constructor(): BasePresenter<INowForecastContract.View>(), INowForecastContract.Presenter