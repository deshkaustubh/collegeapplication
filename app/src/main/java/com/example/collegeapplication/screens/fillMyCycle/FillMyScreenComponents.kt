package com.example.collegeapplication.screens.fillMyCycle


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.collegeapplication.ui.theme.GenderTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CycleTopAppBar(
    gender: GenderTheme,
    onModeToggle: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Fill My Cycle",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            IconButton(onClick = onModeToggle) {
                Icon(
                    imageVector = if (gender == GenderTheme.BOY) Icons.Filled.Male else Icons.Filled.Female,
                    contentDescription = if (gender == GenderTheme.BOY) "Boy Mode" else "Girl Mode",
                    // color is handled by actionIconContentColor above
                )
            }
        }
    )
}

@Composable
fun CheckTable(
    uiState: TableUiState,
    onCellClick: (row: Int, col: Int) -> Unit
) {
    val numRows = 5 // parameters: TK, TD, LI, B, C
    val numCols = 5 // grades: E, VG, G, A, BA
    // For each row and column, draw a cell
    for (row in 0 until numRows) {
        Row {
            for (col in 0 until numCols) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onCellClick(row, col) }
                        .border(1.dp, Color.Gray)
                        .background(if (uiState.checkedCells.contains(row to col)) Color.Green else Color.White)
                ) {
                    if (uiState.checkedCells.contains(row to col)) {
                        Icon(Icons.Default.Check, contentDescription = "Checked")
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryTable(
    state: SummaryTableState
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            listOf("Cycle", "TK", "TD", "LI", "B", "C", "Total").forEach { label ->
                Text(
                    text = label,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
        state.rows.forEach { row ->
            Row(Modifier.fillMaxWidth()) {
                listOf(
                    row.cycleLabel,
                    row.tk.toString(),
                    row.td.toString(),
                    row.li.toString(),
                    row.b.toString(),
                    row.c.toString(),
                    row.total.toString()
                ).forEach { value ->
                    Text(
                        text = value,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun CycleInputSection(
    state: CycleInputUiState,
    onCycle1RangeChange: (Int, Int) -> Unit,
    onCycle2RangeChange: (Int, Int) -> Unit,
    onCycle1MarkChange: (Int, Int?) -> Unit,
    onCycle2MarkChange: (Int, Int?) -> Unit
) {
    // Row for both cycles
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        // Cycle 1
        Column {
            Text("Cycle 1")
            Row {
                Text("Start:")
                TextField(
                    value = state.cycle1Start.toString(),
                    onValueChange = { s -> s.toIntOrNull()?.let { onCycle1RangeChange(it, state.cycle1End) } },
                    modifier = Modifier.width(56.dp)
                )
                Text("End:")
                TextField(
                    value = state.cycle1End.toString(),
                    onValueChange = { e -> e.toIntOrNull()?.let { onCycle1RangeChange(state.cycle1Start, it) } },
                    modifier = Modifier.width(56.dp)
                )
            }
            Spacer(Modifier.height(8.dp))
            // Marks fields
            repeat(state.cycle1Marks.size) { idx ->
                TextField(
                    value = state.cycle1Marks[idx]?.toString() ?: "",
                    onValueChange = { v -> onCycle1MarkChange(idx, v.toIntOrNull()) },
                    label = { Text("${state.cycle1Start + idx}") },
                    modifier = Modifier.width(80.dp)
                )
            }
            Text("Average: ${state.cycle1Average?.toString() ?: ""}")
        }
        // Cycle 2
        Column {
            Text("Cycle 2")
            Row {
                Text("Start:")
                TextField(
                    value = state.cycle2Start.toString(),
                    onValueChange = { s -> s.toIntOrNull()?.let { onCycle2RangeChange(it, state.cycle2End) } },
                    modifier = Modifier.width(56.dp)
                )
                Text("End:")
                TextField(
                    value = state.cycle2End.toString(),
                    onValueChange = { e -> e.toIntOrNull()?.let { onCycle2RangeChange(state.cycle2Start, it) } },
                    modifier = Modifier.width(56.dp)
                )
            }
            Spacer(Modifier.height(8.dp))
            repeat(state.cycle2Marks.size) { idx ->
                TextField(
                    value = state.cycle2Marks[idx]?.toString() ?: "",
                    onValueChange = { v -> onCycle2MarkChange(idx, v.toIntOrNull()) },
                    label = { Text("${state.cycle2Start + idx}") },
                    modifier = Modifier.width(80.dp)
                )
            }
            Text("Average: ${state.cycle2Average?.toString() ?: ""}")
        }
    }
}

@Composable
fun ResetButton(
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onReset,
        modifier = modifier
            .padding(16.dp)
    ) {
        Text("Reset")
    }
}