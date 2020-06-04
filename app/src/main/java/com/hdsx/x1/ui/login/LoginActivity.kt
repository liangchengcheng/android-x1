package com.hdsx.x1.ui.login

import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.hdsx.R
import com.hdsx.x1.ui.base.BaseVmActivity
import com.hdsx.x1.util.core.ActivityManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseVmActivity<LoginViewModel>() {

    override fun layoutRes() = R.layout.activity_login

    override fun viewModelClass() = LoginViewModel::class.java

    override fun initView() {
        // 关闭当前页面
        ivClose.setOnClickListener{
            ActivityManager.finish(LoginActivity::class.java)
        }

        // 注册界面
        tvGoRegister.setOnClickListener {
            // ActivityManager.start(RegisterActivity::class.java)
        }

        // 密码输入框,不是在我们对EditText进行编辑时触发，而是在我们编辑完之后点击软键盘上的各种键才会触发
        tietPassword.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                btnLogin.performClick()
                true
            } else {
                false
            }
        }

        // 登录按钮的点击
        btnLogin.setOnClickListener {
            tilAccount.error = ""
            tilPassword.error = ""
            val account = tietAccount.text.toString()
            val password = tietPassword.text.toString()

            when {
                account.isEmpty() -> tietAccount.error = getString(R.string.account_can_not_be_empty)
                password.isEmpty() -> tilPassword.error = getString(R.string.password_can_not_be_empty)
                else -> mViewModel.login(account, password)
            }
        }
    }

    override fun observe() {
        super.observe()

        /**
         * 以' this '值作为接收方调用指定的函数[block]并返回结果。
         */
        mViewModel.run {
            /**
             * 在给定观察者的生命周期内，将给定观察者添加到观察者列表中
            所有者。事件在主线程上被分派。如果LiveData已经有数据
            设置后，它将被传递给观察者。
            < p >
            观察者只有在所有者位于{@link lifec循环。state #STARTED}中时才会接收事件。
            或{@link生命周期。#恢复}状态(主动)。
            < p >
            如果所有者移动到{@link生命周期。状态#DESTROYED}状态，观察者将
            自动被删除。
            < p >
            当数据改变而{@code所有者}不是活动，它将不会收到任何更新。
            如果它再次激活，它将自动接收最后可用的数据。
            < p >
            LiveData保持对观察者和所有者的强引用
            给定生命周期所有者没有被摧毁。当它被销毁时，LiveData删除引用
            《观察家报》,店主。
            < p >
            如果给定的所有者已经在{@link生命周期中。状态,LiveData #摧毁}
            忽略了电话。
            < p >
            如果给定的所有者观察者元组已经在列表中，那么调用将被忽略。
            如果观察者已经在具有另一个所有者的列表中，LiveData将抛出一个
             */
            submitting.observe(this@LoginActivity, Observer{
                if (it) {
                    showProgressDialog(R.string.logging_in)
                } else{
                    hideProgressDialog()
                }

                // 观察是否获取到了数据
                loginResult.observe(this@LoginActivity, Observer{
                    if (it) {
                        ActivityManager.finish(LoginActivity::class.java)
                    }
                })
            } )
        }

    }
}