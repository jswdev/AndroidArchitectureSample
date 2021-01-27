package com.example.faceeditor.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.faceeditor.App
import com.example.faceeditor.R
import com.example.faceeditor.viewModels.TaskViewModel
import com.example.faceeditor.viewModels.TaskViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class MainActivity : AppCompatActivity(){

    private val scope = MainScope()

    private lateinit var navigation: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initActions()
    }

    override fun onBackPressed() {

        if (drawLayout.isDrawerOpen(sliderView)) {

            drawLayout.closeDrawer(sliderView)
        } else {

            drawLayout.openDrawer(sliderView)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        scope.cancel()
    }

    private fun initActions(){

        topAppBar.setNavigationOnClickListener {

            drawLayout.openDrawer(GravityCompat.START)
        }

        navigation = findNavController(R.id.nav_host_fragment)
        sliderView.setNavigationItemSelectedListener { item ->

            floatingBtn.show()
            drawLayout.closeDrawer(GravityCompat.START)
            when(item.itemId){

                R.id.action_faces -> {

                    //navigation.popBackStack()
                    navigation.navigate(R.id.fragmentFaces)
                }

                R.id.action_logs -> {

                    //navigation.popBackStack()
                    navigation.navigate(R.id.fragmentLogs)
                }
            }
            true
        }
    }
}
