package com.shawky.openweather.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.shawky.openweather.utils.extensions.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseFragment<out ViewModelType : BaseViewModel>(clazz: KClass<ViewModelType>) :
    Fragment() {

    companion object {
        const val POP_TAG = "POP_TAG"
    }

    @get:LayoutRes
    abstract val resId: Int
    val viewModel: ViewModelType by viewModel(clazz)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(resId, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initializeObservers()
        initObservers()
    }

    //abstract fun onApiError(errorResult: Failure)
    abstract fun initObservers()

    abstract fun initViews()

    private fun initializeObservers() {
        viewModel.errorObserver.observe(viewLifecycleOwner, Observer {
            val errorResult = it ?: return@Observer
            hideLoading()

            when (errorResult) {
            }
        })

        viewModel._networkState.observe(viewLifecycleOwner, Observer {
            val state = it ?: return@Observer
            when (state) {

            }

            /*if (it) showLoading()
            else hideLoading()*/
        })
    }

    fun showLoading() {
        //pop.show(parentFragmentManager, POP_TAG)
    }

    fun hideLoading() {
        //pop.dismiss()
    }

    fun showError(@StringRes errorString: Int) {
        showError(getString(errorString))
    }

    fun showError(errorString: String) {
        context?.toast(errorString)
    }

    fun showMessage(@StringRes errorString: Int) {
        showError(getString(errorString))
    }

    fun showMessage(errorString: String) {
        context?.toast(errorString)
    }
}
