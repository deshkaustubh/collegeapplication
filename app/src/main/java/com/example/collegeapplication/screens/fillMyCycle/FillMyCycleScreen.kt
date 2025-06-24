package com.example.collegeapplication.screens.fillMyCycle

import androidx.compose.ui.tooling.preview.Preview


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FillMyCycleScreen(
    viewModel: FillMyCycleViewModel
) {
    val cycleInputUiState by viewModel.cycleInputUiState.collectAsState()
    val tableUiState by viewModel.tableUiState.collectAsState()
    val summaryTableState by viewModel.summaryTableState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(
                state = rememberScrollState(),
                enabled = true,
            )
    ) {
        // Top Bar (you can replace with your own TopAppBar if needed)
        Text(
            "Fill My Cycle",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(8.dp))

        // Cycle Input Section
        CycleInputSection(
            state = cycleInputUiState,
            onCycle1RangeChange = viewModel::updateCycle1Range,
            onCycle2RangeChange = viewModel::updateCycle2Range,
            onCycle1MarkChange = viewModel::updateCycle1Mark,
            onCycle2MarkChange = viewModel::updateCycle2Mark
        )
        Spacer(Modifier.height(16.dp))

        // Performance Table
        Text("Performance Table", style = MaterialTheme.typography.titleMedium)
        CheckTable(
            uiState = tableUiState,
            onCellClick = viewModel::toggleTableCell,
//            numRows = 5, // or dynamic if your table supports it
//            numCols = 5
        )
        Spacer(Modifier.height(16.dp))

        // Summary Table
        Text("Summary", style = MaterialTheme.typography.titleMedium)
        SummaryTable(state = summaryTableState)
        Spacer(Modifier.height(24.dp))

        // Reset Button
        Button(
            onClick = viewModel::resetAll,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Reset")
        }
    }
}

@Preview(showBackground = true, widthDp = 420, heightDp = 900)
@Composable
fun FillMyCycleScreenPreview() {
    // Use a fake ViewModel for preview with default state
    val fakeViewModel = remember { FillMyCycleViewModel() }
    FillMyCycleScreen(viewModel = fakeViewModel)
}