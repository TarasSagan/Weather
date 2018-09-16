package com.example.presentation.mvp

interface IBasePresenter<View: IBaseView>{
    var mView: View?

    fun onAttach(view: View){
        mView = view
    }

    fun onDetach(){
        mView = null
    }
}