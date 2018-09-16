package com.example.presentation.presenters.main

import com.example.presentation.mvp.IBasePresenter
import com.example.presentation.mvp.IBaseView

interface IMainViewContract{
    interface View: IBaseView
    interface Presenter: IBasePresenter<IMainViewContract.View>
}