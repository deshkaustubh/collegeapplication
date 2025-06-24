package com.example.collegeapplication.screens.fillMyCycle


data class TableUiState(
    val checkedCells: Set<Pair<Int, Int>> = emptySet() // (row, col) pairs
)


data class SummaryRow(
    val cycleLabel: String,
    val tk: Int = 0,
    val td: Int = 0,
    val li: Int = 0,
    val b: Int = 0,
    val c: Int = 0,
    val total: Int = 0
)

data class SummaryTableState(
    val rows: List<SummaryRow> = emptyList()
)


data class CycleInputUiState(
    val cycle1Start: Int = 1,
    val cycle1End: Int = 5,
    val cycle2Start: Int = 6,
    val cycle2End: Int = 10,
    val cycle1Marks: List<Int?> = List(5) { null }, // nullable for empty fields
    val cycle2Marks: List<Int?> = List(5) { null },
    val cycle1Average: Double? = null,
    val cycle2Average: Double? = null
)