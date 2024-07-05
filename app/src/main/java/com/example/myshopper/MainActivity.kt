package com.example.myshopper

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.myshopper.ui.CheckoutFragment
import com.example.myshopper.ui.ProductsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val TAG = "MainActivity" 

class MainActivity : AppCompatActivity() {
    private var bottomNav: BottomNavigationView? = null
    private var productFrag: ProductsFragment = ProductsFragment()
    private var checkFrag: CheckoutFragment = CheckoutFragment()
    private var frame: FrameLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: onCreate Called!")
        frame = findViewById(R.id.frame)
        bottomNav = findViewById(R.id.bottom_nav)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        if (savedInstanceState == null) {
            loadFragmrnt(productFrag)
        }

        bottomNav!!.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_products -> {
                    loadFragmrnt(productFrag)
                    return@setOnItemSelectedListener true
                }

                R.id.action_checkout -> {
                    if (checkFrag.arguments == null){
                        loadFragmrnt(checkFrag)
                    } else {
                        navigateToNewFragment(checkFrag.requireArguments())
                    }
//                    checkFrag.arguments?.getBundle(null)?.let { it1 -> navigateToNewFragment(it1)

                    true
                }

                else -> {
                    return@setOnItemSelectedListener true
                }
            }

        }
    }

    private fun loadFragmrnt(productFrag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, productFrag)
            .commit()
    }

    fun navigateToNewFragment(bundle: Bundle) {
        val newFragment = CheckoutFragment()
        newFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, newFragment)
            .addToBackStack(null)
            .commit()
    }

}