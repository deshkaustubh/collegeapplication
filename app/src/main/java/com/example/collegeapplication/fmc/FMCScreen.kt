package com.example.collegeapplication.fmc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.collegeapplication.ui.theme.CollegeApplicationTheme
import com.example.collegeapplication.ui.theme.GenderTheme

@Composable
fun FMCScreen() {
    // State to track the current gender theme
    val currentGender = remember { mutableStateOf(GenderTheme.BOY) }

    // Apply the theme based on the current gender
    CollegeApplicationTheme(gender = currentGender.value) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(
            state = rememberScrollState()
        )) {
            // Pass the current gender and toggle function to the top app bar
            FMCTopAppBar(
                gender = currentGender.value,
                onModeToggle = {
                    // Toggle between BOY and GIRL themes
                    currentGender.value = if (currentGender.value == GenderTheme.BOY) {
                        GenderTheme.GIRL
                    } else {
                        GenderTheme.BOY
                    }
                }
            )
            // Display the cycle tables with input fields
            CycleRow()
        }
    }
}

// You can add a preview for this screen if needed
