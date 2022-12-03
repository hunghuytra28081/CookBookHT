package com.example.cookbookht.ui.signup

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cookbookht.R
import com.example.cookbookht.data.repository.source.local.entities.User
import com.example.cookbookht.extension.noWhiteSpace
import com.example.cookbookht.extension.passwordHoa
import com.example.cookbookht.extension.passwordNumber
import com.example.cookbookht.ui.login.LoginActivity
import com.example.cookbookht.ui.login.LoginViewModel
import com.example.cookbookht.ui.search.SearchViewModel
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initView()
        initListener()
    }

    private fun initListener() {
        btnSignUp.setOnClickListener {
            if (validateFullName() and validateUserName() and validatePassword() and validateRePassword()) {
                val fullName = regFullName.editText?.text.toString()
                val username = regUsername.editText?.text.toString()
                val password = regPassword.editText?.text.toString()

                loginViewModel.insertUser(
                    User(
                        fullName = fullName,
                        userName = username,
                        password = password
                    )
                )
                Toast.makeText(this, resources.getString(R.string.sign_up_success), Toast.LENGTH_LONG)
                    .show()
                finish()
            }

        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun initView() {
        btnCallLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            val pairs: Array<Pair<View, String>?> = arrayOfNulls(7)
            pairs[0] = Pair(imvRegBanner, "banner_trans")
            pairs[1] = Pair(tvRegLogo, "logo_trans")
            pairs[2] = Pair(tvSignup, "sign_trans")
            pairs[3] = Pair(regUsername, "username_trans")
            pairs[4] = Pair(regPassword, "password_trans")
            pairs[5] = Pair(btnSignUp, "go_trans")
            pairs[6] = Pair(btnCallLogin, "call_trans")

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

    private fun validateFullName(): Boolean {
        val fullName = regFullName.editText?.text.toString()
        return if (fullName.isEmpty()) {
            regFullName.error = resources.getString(R.string.no_full_name)
            false
        } else {
            regFullName.error = null
            regFullName.isErrorEnabled = false
            true
        }
    }

    private fun validateUserName(): Boolean {
        val username = regUsername.editText?.text.toString()
        val noWhiteSpace = "\\A\\w{4,20}\\z"
        return if (username.isEmpty()) {
            regUsername.error = resources.getString(R.string.no_user_name)
            false
        } else if (username.length > 15) {
            regUsername.error = resources.getString(R.string.no_than_15_word)
            false
        } else if (username.contains(" ")) {
            regUsername.error = resources.getString(R.string.no_space)
            false
        } else {
            regUsername.error = null
            regUsername.isErrorEnabled = false
            true
        }
    }

    private fun validatePassword(): Boolean {
        val password = regPassword.editText?.text.toString()
//        val passwordHoa = "(?=.*[A-Z])"
//        val passwordNumber = "(?=.*[0-9])"
//        val noWhiteSpace = "\\A\\w{4,20}\\z"
        val pass4Number = ".{4,}"
        //                "^" +
//                "(?=.*[0-9])" +         //ít nhất 1 chữ số
//                "(?=.*[a-z])" +         //ít nhất 1 chữ thường
//                "(?=.*[A-Z])" +         //ít nhất 1 chữ hoa
//                "(?=.*[a-zA-Z])" +      //bất kỳ chữ cái nào
//                "(?=.*[@#$%^&+=])" +    //ít nhất 1 ký tự đặc biệt
//                "(?=\\S+$)" +           //không có khoảng trắng
//                ".{4,}"              //ít nhất 4 ký tự
//                "$"
//                ;
        return if (password.isEmpty()) {
            regPassword.error = resources.getString(R.string.no_pass)
            false
        } else if (password.contains(" ")) {
            regPassword.error = resources.getString(R.string.no_space)
            false
        }
//        else if (!password.passwordHoa()) {
//            regPassword.error = resources.getString(R.string.has_1_uppercase)
//            false
//        } else if (!password.passwordNumber()) {
//            regPassword.error = resources.getString(R.string.has_1_number)
//            false
//        }
        else if (password.length < 6) {
            regPassword.error = resources.getString(R.string.pass_to_short)
            false
        } else {
            regPassword.error = null
            regPassword.isErrorEnabled = false
            true
        }
    }

    private fun validateRePassword(): Boolean {
        val password = regPassword.editText?.text.toString()
        val rePassword = regPasswordAgain.editText!!.text.toString()

        val noWhiteSpace = "\\A\\w{4,20}\\z"

        return if (rePassword.isEmpty()) {
            regPasswordAgain.error = resources.getString(R.string.no_pass)
            false
        } else if (rePassword.contains(" ")) {
            regPasswordAgain.error = resources.getString(R.string.no_space)
            false
        } else if (password != rePassword) {
            regPasswordAgain.error = resources.getString(R.string.pass_wrong)
            false
        } else {
            regPasswordAgain.error = null
            regPasswordAgain.isErrorEnabled = false
            true
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}