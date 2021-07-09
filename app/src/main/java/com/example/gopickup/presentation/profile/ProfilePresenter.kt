package com.example.gopickup.presentation.profile

import android.util.Log
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.model.repository.AppRepositoryImpl
import com.example.gopickup.model.request.EditProfile
import com.example.gopickup.model.request.NewImage
import com.example.gopickup.model.response.Profile
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfilePresenter(
    private val view: ProfileContract.View,
    private val appRepositoryImpl: AppRepositoryImpl
) : ProfileContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        view.initView()
    }

    override fun getProfile(profileRequest: BaseRequest<String>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.getProfile(profileRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showProfile(it.data)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("ProfilePresenter", "ERROR, getProfile: ${it.localizedMessage}")
                }
            ))
    }

    override fun postEditProfile(profile: BaseRequest<EditProfile>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.postEditProfile(profile)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showEditProfileSuccess(it.info!!)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("ProfilePresenter", "ERROR, postEditProfile: ${it.localizedMessage}")
                }
            ))
    }

    override fun postEditPhotoProfile(newImage: BaseRequest<NewImage>) {
        view.showLoading()
        compositeDisposable.add(appRepositoryImpl.postEditPhotoProfile(newImage)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    when (it.code) {
                        StatusCode.SUCCESS -> view.showEditPhotoProfileSuccess(it.info!!)
                        StatusCode.SESSION_EXPIRED -> view.showSessionExpired(it.info)
                        else -> view.showMessage(it.info)
                    }
                },
                {
                    view.hideLoading()
                    view.showMessage(Constants.DEFAULT_ERROR_MSG)
                    Log.e("ProfilePresenter", "ERROR, postEditPhotoProfile: ${it.localizedMessage}")
                }
            ))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}