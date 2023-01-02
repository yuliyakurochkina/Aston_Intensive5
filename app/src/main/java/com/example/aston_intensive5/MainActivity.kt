package com.example.aston_intensive5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getResId())
        val second = findViewById<View?>(R.id.fragment_detail_container)

        var hasSecondFragmentContainer = false

        second?.let {
            hasSecondFragmentContainer = true
        }

        supportFragmentManager.beginTransaction().run {
            replace(
                R.id.fragment_container,
                ListOfContactsFragment.newListFragment(hasSecondFragmentContainer),
                null
            )
            commit()
        }
    }

    @LayoutRes
    private fun getResId(): Int {
        return R.layout.activity_masterdetail
    }
}