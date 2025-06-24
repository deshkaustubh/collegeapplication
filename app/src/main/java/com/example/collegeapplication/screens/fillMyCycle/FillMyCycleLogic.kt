package com.example.collegeapplication.screens.fillMyCycle

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

