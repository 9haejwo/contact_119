package com.example.a115make

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a115make.databinding.ActivityMainBinding
import com.example.a115make.databinding.ActivityMypageBinding

class Mypage : AppCompatActivity() {

    private lateinit var binding :ActivityMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMypageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}