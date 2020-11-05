package com.ravirawal.statement

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import com.ravirawal.statement.base.BaseActivity
import com.ravirawal.statement.databinding.SplashActivityBinding
import com.ravirawal.statement.splash.ui.SplashFragment


class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        super.onCreate(savedInstanceState)
        setContentView(SplashActivityBinding.inflate(layoutInflater).root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.container, SplashFragment())
            }
        }
    }
}