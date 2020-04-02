package com.zomato.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.zomato.R
import com.zomato.databinding.ViewLoaderBinding

class ProgressDialog constructor(
    context: Context
) : Dialog(context) {

    private lateinit var mBinding: ViewLoaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.requestFeature(Window.FEATURE_NO_TITLE)

        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = DataBindingUtil.inflate(inflater, R.layout.view_loader, null, false)

        setContentView(mBinding.root)

        setCancelable(false)
        setCanceledOnTouchOutside(false)

    }


    fun setMessage(message: String?) {
        mBinding.tvMessage.text = message ?: context.getString(R.string.loading)
    }

    fun setDialogOrientation(orientation: ORIENTATION) {
        val loaderView = findViewById<LinearLayout>(R.id.lly_loader_view)
        loaderView.orientation = orientation.value
    }

    enum class ORIENTATION(val value: Int) {
        HORIZONTAL(0), VERTICAL(1);
    }

}
