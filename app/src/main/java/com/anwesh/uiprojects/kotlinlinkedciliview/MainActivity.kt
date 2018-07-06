package com.anwesh.uiprojects.kotlinlinkedciliview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.linkedciliview.LinkedCiLiView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LinkedCiLiView.create(this)
    }
}
