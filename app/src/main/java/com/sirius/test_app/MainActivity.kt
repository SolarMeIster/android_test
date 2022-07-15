package com.sirius.test_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sirius.test_app.itemOfRecyclerView.*

class MainActivity : AppCompatActivity() {

    private lateinit var imageViewOfApp: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var activityMain: View
    private lateinit var buttonBack: FloatingActionButton
    private lateinit var buttonSettings: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageViewOfApp = findViewById(R.id.imageViewOfApp)
        recyclerView = findViewById(R.id.recyclerView)
        activityMain = findViewById(R.id.activity_main)
        buttonBack = findViewById(R.id.buttonBack)
        buttonSettings = findViewById(R.id.buttonSettings)
        hideSystemUI()
        Glide.with(this)
            .load(DataModel().image)
            .into(imageViewOfApp)
    }

    override fun onResume() {
        super.onResume()
        buttonBack.setOnClickListener {
            Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show()
        }

        buttonSettings.setOnClickListener {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
        }

        val adapter = RecyclerViewAdapter(createAdapterData(), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun createAdapterData(): List<BaseApp> {
        return listOf(
            MainHeader(DataModel().name, DataModel().rating, DataModel().gradeCnt, DataModel().logo),
            Genre(DataModel().tags),
            Description(DataModel().description),
            Videos(DataModel().videos),
            Header("Review & Ratings"),
            Rating(DataModel().rating, DataModel().gradeCnt),
            DataModel().reviews[0],
            DataModel().reviews[1],
            Install(DataModel().action.name, DataModel().action.action)
        )
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, activityMain).let { controller ->
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}