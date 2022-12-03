package com.example.cookbookht.ui.login

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.cookbookht.R
import com.example.cookbookht.extension.noWhiteSpace
import com.example.cookbookht.sharePreference.Preferences
import com.example.cookbookht.ui.main.MainActivity
import com.example.cookbookht.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()
    private val prefs: Preferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        initView()
        initListener()
    }

    private fun initListener() {
        btnLogin.setOnClickListener {
            if (validateUserName() and validatePassword()) {
                val username = logUsername.editText?.text.toString()
                val password = logPassword.editText?.text.toString()

                try {
                    val loginUser = loginViewModel.checkUser(username, password)
                    if (loginUser == null) {
                        logUsername.error = resources.getString(R.string.user_wrong)
                    } else {
                        logUsername.error = null
                        logUsername.isErrorEnabled = false
                        progress_bar.isVisible = true
                        prefs.isLoginSuccess.set(true)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun initView() {
        btnCallSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            val pairs: Array<Pair<View, String>?> = arrayOfNulls(7)
            pairs[0] = Pair(imvLogBanner, "banner_trans")
            pairs[1] = Pair(tvLogLogo, "logo_trans")
            pairs[2] = Pair(tvLogin, "sign_trans")
            pairs[3] = Pair(logUsername, "username_trans")
            pairs[4] = Pair(logPassword, "password_trans")
            pairs[5] = Pair(btnLogin, "go_trans")
            pairs[6] = Pair(btnCallSignup, "call_trans")

            logUsername.error = null
            logUsername.isErrorEnabled = false
            logPassword.error = null
            logPassword.isErrorEnabled = false

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val option = ActivityOptions.makeSceneTransitionAnimation(
                    this, *pairs
                )
                startActivity(intent, option.toBundle())
            } else {
                startActivity(intent)
            }
        }
    }

    private fun validateUserName(): Boolean {
        val username = logUsername.editText?.text.toString()
        val noWhiteSpace = "\\A\\w{4,20}\\z"
        return if (username.isEmpty()) {
            logUsername.error = resources.getString(R.string.no_user_name)
            false
        } else if (username.contains(" ")) {
            logUsername.error = resources.getString(R.string.no_space)
            false
        } else {
            logUsername.error = null
            logUsername.isErrorEnabled = false
            true
        }
    }

    private fun validatePassword(): Boolean {
        val password = logPassword.editText?.text.toString()
        val noWhiteSpace = "\\A\\w{4,20}\\z"

        return if (password.isEmpty()) {
            logPassword.error = resources.getString(R.string.no_pass)
            false
        } else if (password.contains(" ")) {
            logPassword.error = resources.getString(R.string.no_space)
            false
        } else {
            logPassword.error = null
            logPassword.isErrorEnabled = false
            true
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}