package com.zomato.view.baseview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.zomato.view.ProgressDialog
import com.zomato.viewmodel.BaseViewModel

abstract class BaseFragment<T : ViewDataBinding, VM : ViewModel> : AppCompatDialogFragment() {

    lateinit var mBinding: T
    protected lateinit var viewModel: VM

    private var mProgressDialog: ProgressDialog? = null

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun createViewModel(): VM

    abstract fun inject();

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, layoutId(),
            container, false
        )
        mBinding.lifecycleOwner = this

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = createViewModel()

        (viewModel as BaseViewModel).mLoading
            .observe(viewLifecycleOwner,
                Observer {
                    if (it.first) showLoader(it.second) else hideLoader()
                })

        (viewModel as BaseViewModel).mShowMessage
            .observe(viewLifecycleOwner,
                Observer {
                    showMessage(it)
                })

        initView(savedInstanceState)
    }

    private fun showMessage(message: String) {
        activity?.let { it1 ->
            AlertDialog.Builder(it1)
                .setMessage(message)
                .setCancelable(true)
                .create()
                .show()
        }

    }

    protected fun showLoader(message: String?) {
        if (mProgressDialog == null)
            mProgressDialog = activity?.let {
                ProgressDialog(
                    it
                )
            }

        mProgressDialog?.show()
        mProgressDialog?.setMessage(message)


    }

    protected fun hideLoader() {
        mProgressDialog?.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideLoader()
        mProgressDialog = null
    }
}