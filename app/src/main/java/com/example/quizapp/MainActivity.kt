package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.quizapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var xenoBind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        xenoBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(xenoBind.root)
    }
}

