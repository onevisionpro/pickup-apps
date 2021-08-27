package com.example.gopickup.presentation.about_apps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.core.text.HtmlCompat
import com.example.gopickup.R
import com.example.gopickup.base.BaseActivity
import com.example.gopickup.base.BaseRequest
import com.example.gopickup.databinding.ActivityAboutAppsBinding
import com.example.gopickup.model.request.Type
import com.example.gopickup.model.response.AboutApps
import com.example.gopickup.utils.MoreType

class AboutAppsActivity : BaseActivity(), AboutAppsContract.View {

    private var _binding: ActivityAboutAppsBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: AboutAppsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAboutAppsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = AboutAppsPresenter(this, callApi())
        presenter.start()
        presenter.getWordings(moreType = BaseRequest(
            guid = provideGUID(),
            code = "",
            data = Type(type = MoreType.ABOUT)
        ))
    }

    override fun initView() {
        super.initView()
        initProgressBar(binding.progressBar)
        binding.toolbar.tvToolbarTitle.text = "About Apps"
        binding.toolbar.icBack.setOnClickListener { finish() }
    }

    override fun showWordings(aboutApps: AboutApps) {
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.loadData(aboutApps.html!!, "text/html; charset=utf-8", "UTF-8");
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }
}