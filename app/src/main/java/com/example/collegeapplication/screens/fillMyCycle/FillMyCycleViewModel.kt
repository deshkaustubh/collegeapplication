package com.example.collegeapplication.screens.fillMyCycle

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow




class FillMyCycleViewModel : ViewModel() {

    // --- Cycle Input State ---
    private val _cycleInputUiState = MutableStateFlow(CycleInputUiState())
    val cycleInputUiState: StateFlow<CycleInputUiState> = _cycleInputUiState

    // --- Performance Table Check State (for both cycles, optional extension: use two TableUiStates) ---
    private val _tableUiState = MutableStateFlow(TableUiState())
    val tableUiState: StateFlow<TableUiState> = _tableUiState

    // --- Calculation Result State for each cycle ---
    private val _cycle1Result = MutableStateFlow<List<Int>?>(null)
    val cycle1Result: StateFlow<List<Int>?> = _cycle1Result

    private val _cycle2Result = MutableStateFlow<List<Int>?>(null)
    val cycle2Result: StateFlow<List<Int>?> = _cycle2Result

    // --- Summary Table State ---
    private val _summaryTableState = MutableStateFlow(SummaryTableState())
    val summaryTableState: StateFlow<SummaryTableState> = _summaryTableState

    // --- Cycle Range & Marks Logic ---

    fun updateCycle1Range(start: Int, end: Int) {
        val newMarks = List((end - start + 1).coerceAtLeast(0)) { null }
        _cycleInputUiState.value = _cycleInputUiState.value.copy(
            cycle1Start = start,
            cycle1End = end,
            cycle1Marks = newMarks
        )
        calculateCycle1Average()
        updateCycle1Calculation()
        updateSummaryTable()
    }

    fun updateCycle2Range(start: Int, end: Int) {
        val newMarks = List((end - start + 1).coerceAtLeast(0)) { null }
        _cycleInputUiState.value = _cycleInputUiState.value.copy(
            cycle2Start = start,
            cycle2End = end,
            cycle2Marks = newMarks
        )
        calculateCycle2Average()
        updateCycle2Calculation()
        updateSummaryTable()
    }

    fun updateCycle1Mark(index: Int, value: Int?) {
        val marks = _cycleInputUiState.value.cycle1Marks.toMutableList()
        if (index in marks.indices) {
            marks[index] = value
        }
        _cycleInputUiState.value = _cycleInputUiState.value.copy(cycle1Marks = marks)
        calculateCycle1Average()
        updateCycle1Calculation()
        updateSummaryTable()
    }

    fun updateCycle2Mark(index: Int, value: Int?) {
        val marks = _cycleInputUiState.value.cycle2Marks.toMutableList()
        if (index in marks.indices) {
            marks[index] = value
        }
        _cycleInputUiState.value = _cycleInputUiState.value.copy(cycle2Marks = marks)
        calculateCycle2Average()
        updateCycle2Calculation()
        updateSummaryTable()
    }

    private fun calculateCycle1Average() {
        val marks = _cycleInputUiState.value.cycle1Marks.filterNotNull()
        val avg = if (marks.isNotEmpty()) marks.average() else null
        _cycleInputUiState.value = _cycleInputUiState.value.copy(cycle1Average = avg)
    }

    private fun calculateCycle2Average() {
        val marks = _cycleInputUiState.value.cycle2Marks.filterNotNull()
        val avg = if (marks.isNotEmpty()) marks.average() else null
        _cycleInputUiState.value = _cycleInputUiState.value.copy(cycle2Average = avg)
    }

    // --- Calculation Logic Integration ---

    // Replace this with your actual logic as needed
    private fun calculateCycle(marks: Int?): List<Int>? {
        // Replace with your own calculation logic or call CycleCalculator.calculateCycle(marks)
        return marks?.let { CycleCalculator.calculateCycle(it) }
    }

    private fun updateCycle1Calculation() {
        // If using average marks for calculation
        val avg = _cycleInputUiState.value.cycle1Average?.toInt()
        _cycle1Result.value = calculateCycle(avg)
    }

    private fun updateCycle2Calculation() {
        val avg = _cycleInputUiState.value.cycle2Average?.toInt()
        _cycle2Result.value = calculateCycle(avg)
    }

    // --- Table Check/Tick Logic ---

    fun toggleTableCell(row: Int, col: Int) {
        val current = _tableUiState.value.checkedCells
        val newSet = if (current.contains(row to col)) {
            current - (row to col)
        } else {
            current + (row to col)
        }
        _tableUiState.value = _tableUiState.value.copy(checkedCells = newSet)
    }

    fun resetTableTicks() {
        _tableUiState.value = TableUiState()
    }

    // --- Summary Table Logic ---

    private fun updateSummaryTable() {
        val c1 = _cycle1Result.value
        val c2 = _cycle2Result.value

        val row1 = if (c1 != null) {
            SummaryRow(
                cycleLabel = "${_cycleInputUiState.value.cycle1Start} to ${_cycleInputUiState.value.cycle1End}",
                tk = c1.getOrNull(0) ?: 0,
                td = c1.getOrNull(1) ?: 0,
                li = c1.getOrNull(2) ?: 0,
                b = c1.getOrNull(3) ?: 0,
                c = c1.getOrNull(4) ?: 0,
                total = c1.getOrNull(5) ?: 0
            )
        } else {
            SummaryRow(
                cycleLabel = "${_cycleInputUiState.value.cycle1Start} to ${_cycleInputUiState.value.cycle1End}"
            )
        }

        val row2 = if (c2 != null) {
            SummaryRow(
                cycleLabel = "${_cycleInputUiState.value.cycle2Start} to ${_cycleInputUiState.value.cycle2End}",
                tk = c2.getOrNull(0) ?: 0,
                td = c2.getOrNull(1) ?: 0,
                li = c2.getOrNull(2) ?: 0,
                b = c2.getOrNull(3) ?: 0,
                c = c2.getOrNull(4) ?: 0,
                total = c2.getOrNull(5) ?: 0
            )
        } else {
            SummaryRow(
                cycleLabel = "${_cycleInputUiState.value.cycle2Start} to ${_cycleInputUiState.value.cycle2End}"
            )
        }

        val totalRow = SummaryRow(
            cycleLabel = "Total",
            tk = row1.tk + row2.tk,
            td = row1.td + row2.td,
            li = row1.li + row2.li,
            b = row1.b + row2.b,
            c = row1.c + row2.c,
            total = row1.total + row2.total
        )

        val avgRow = SummaryRow(
            cycleLabel = "Average",
            tk = ((row1.tk + row2.tk) / 2),
            td = ((row1.td + row2.td) / 2),
            li = ((row1.li + row2.li) / 2),
            b = ((row1.b + row2.b) / 2),
            c = ((row1.c + row2.c) / 2),
            total = ((row1.total + row2.total) / 2)
        )

        _summaryTableState.value = SummaryTableState(
            rows = listOf(row1, row2, totalRow, avgRow)
        )
    }

    // --- Reset Functionality ---

    fun resetAll() {
        _cycleInputUiState.value = CycleInputUiState()
        _cycle1Result.value = null
        _cycle2Result.value = null
        _tableUiState.value = TableUiState()
        _summaryTableState.value = SummaryTableState()
    }

    // --- Utility for calculation or raw marks ---
    fun getAllCycleMarks(): Pair<List<Int?>, List<Int?>> {
        return _cycleInputUiState.value.cycle1Marks to _cycleInputUiState.value.cycle2Marks
    }

    object CycleCalculator {
        /**
         * Calculates the weighted breakdown of a marks input for a cycle.
         * Returns a list: [w0, w1, w2, w3, w4, total, originalMarks]
         * - w0..w4: Weighted values for each parameter
         * - total: sum of all weighted values
         * - originalMarks: the marks input (possibly rounded up to even)
         */
        fun calculateCycle(marksInput: Int): List<Int>? {
            var marks = marksInput
            // Validate input: must be even and between 20 and 100
            if (marks < 20 || marks > 100) return null

            // If odd, round up to even
            if (marks % 2 != 0) marks += 1
            if (marks > 100) marks = 100

            // Parameter weights for each row
            val weights = intArrayOf(6, 6, 2, 2, 4)
            // Calculate initial l values (number of times each weight is used)
            val l = IntArray(5) { marks / 20 + if (marks % 20 != 0) 1 else 0 }
            val diff = if (marks % 20 != 0) 20 - (marks % 20) else 0

            // Adjust l values to account for the remainder (diff)
            var tempDiff = diff
            if (tempDiff >= 6) { l[1]--; tempDiff -= 6 }
            if (tempDiff >= 4) { l[4]--; tempDiff -= 4 }
            if (tempDiff in 2..4) { l[2]--; tempDiff -= 2 }
            if (tempDiff >= 6) { l[0]--; tempDiff -= 6 }
            if (tempDiff >= 2) { l[3]--; tempDiff -= 2 }

            // Calculate weighted scores
            val w = IntArray(5) { weights[it] * l[it] }
            val total = w.sum()
            return w.toList() + total + marks
        }
    }


}