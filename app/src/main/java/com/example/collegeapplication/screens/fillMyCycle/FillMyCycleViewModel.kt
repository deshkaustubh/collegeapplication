package com.example.collegeapplication.screens.fillMyCycle

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object CycleCalculator {
    fun calculateCycle(marksInput: Int): List<Int>? {
        var marks = marksInput
        if (marks < 20 || marks > 100) return null

        if (marks % 2 != 0) marks += 1
        if (marks > 100) marks = 100

        val weights = intArrayOf(6, 6, 2, 2, 4)
        val l = IntArray(5) { marks / 20 + if (marks % 20 != 0) 1 else 0 }
        var diff = if (marks % 20 != 0) 20 - (marks % 20) else 0

        var tempDiff = diff
        if (tempDiff >= 6) { l[1]--; tempDiff -= 6 }
        if (tempDiff >= 4) { l[4]--; tempDiff -= 4 }
        if (tempDiff in 2..4) { l[2]--; tempDiff -= 2 }
        if (tempDiff >= 6) { l[0]--; tempDiff -= 6 }
        if (tempDiff >= 2) { l[3]--; tempDiff -= 2 }

        val w = IntArray(5) { weights[it] * l[it] }
        val total = w.sum()
        return w.toList() + total + marks
    }
}


class FillMyCycleViewModel : ViewModel() {

    // Holds the calculation result for the UI
    private val _cycleResult = MutableStateFlow<List<Int>?>(null)
    val cycleResult: StateFlow<List<Int>?> = _cycleResult

    fun onMarksInput(marks: Int) {
        _cycleResult.value = CycleCalculator.calculateCycle(marks)
    }
}