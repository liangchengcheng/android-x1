package com.hdsx.x1.ui.settings

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.hdsx.BuildConfig
import androidx.core.view.isVisible
import com.hdsx.R
import com.hdsx.x1.common.SeekBarChangeListenerAdapter
import com.hdsx.x1.ext.setNavigationBarColor
import com.hdsx.x1.ext.showToast
import com.hdsx.x1.model.bean.Article
import com.hdsx.x1.model.store.SettingsStore
import com.hdsx.x1.ui.base.BaseVmActivity
import com.hdsx.x1.ui.detail.DetailActivity
import com.hdsx.x1.ui.detail.DetailActivity.Companion.PARAM_ARTICLE
import com.hdsx.x1.ui.login.LoginActivity
import com.hdsx.x1.util.clearCache
import com.hdsx.x1.util.core.ActivityManager
import com.hdsx.x1.util.getCacheSize
import com.hdsx.x1.util.isNightMode
import com.hdsx.x1.util.setNightMode
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.view_change_text_zoom.*

@SuppressLint("SetTextI18n")
class SettingsActivity : BaseVmActivity<SettingsViewModel>() {

    override fun layoutRes() = R.layout.activity_settings

    override fun viewModelClass() = SettingsViewModel::class.java

    override fun initView() {

        setNavigationBarColor(getColor(R.color.bgColorSecondary))

        scDayNight.isChecked = isNightMode(this)
        tvFontSize.text = "${SettingsStore.getWebTextZoom()}%"
        tvClearCache.text = getCacheSize(this)
        tvAboutUs.text = getString(R.string.current_version, BuildConfig.VERSION_NAME)

        ivBack.setOnClickListener { ActivityManager.finish(SettingsActivity::class.java) }

        scDayNight.setOnCheckedChangeListener { _, checked ->
            setNightMode(checked)
            SettingsStore.setNightMode(checked)
        }

        llFontSize.setOnClickListener {
            setFontSize()
        }

        llClearCache.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.confirm_clear_cache)
                .setPositiveButton(R.string.confirm) { _, _ ->
                    clearCache(this)
                    tvClearCache.text = getCacheSize(this)
                }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .show()
        }

        // TODO 检查版本
        llCheckVersion.setOnClickListener {
            showToast(getString(R.string.stay_tuned))
        }

        llAboutUs.setOnClickListener {
            ActivityManager.start(
                DetailActivity::class.java,
                mapOf(
                    PARAM_ARTICLE to Article(
                        title = getString(R.string.abount_us),
                        link = "https://github.com/xiaoyanger0825/wanandroid"
                    )
                )
            )
        }

        tvLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.confirm_logout)
                .setPositiveButton(R.string.confirm) { _, _ ->
                    mViewModel.logout()
                    ActivityManager.start(LoginActivity::class.java)
                    ActivityManager.finish(SettingsActivity::class.java)
                }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .show()
        }
    }

    private fun setFontSize() {
        val textZoom = SettingsStore.getWebTextZoom()
        var tempTextZoom = textZoom
        AlertDialog.Builder(this)
            .setTitle(R.string.font_size)
            .setView(LayoutInflater.from(this).inflate(R.layout.view_change_text_zoom, null).apply {
                seekBar.progress = textZoom - 50
                seekBar.setOnSeekBarChangeListener(SeekBarChangeListenerAdapter(
                    onProgressChanged = { _, progress, _ ->
                        tempTextZoom = 50 + progress
                        tvFontSize.text = "$tempTextZoom%"
                    }
                ))
            })
            .setNegativeButton(R.string.cancel) { _, _ ->
                tvFontSize.text = "$textZoom%"
            }
            .setPositiveButton(R.string.confirm) { _, _ ->
                SettingsStore.setWebTextZoom(tempTextZoom)
            }
            .show()

    }

    override fun initData() {
        mViewModel.getLoginStatus()
    }

    override fun observe() {
        super.observe()
        mViewModel.isLogin.observe(this, Observer {
            tvLogout.isVisible = it
        })
    }
}
