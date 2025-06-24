package com.example.collegeapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.collegeapplication.screens.fillMyCycle.FillMyCycleScreen
import com.example.collegeapplication.screens.fillMyCycle.FillMyCycleViewModel
import com.example.collegeapplication.ui.theme.CollegeApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CollegeApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    FillMyCycleScreen(
                        viewModel = viewModel<FillMyCycleViewModel>()
                    )
                }
            }
        }
    }
}