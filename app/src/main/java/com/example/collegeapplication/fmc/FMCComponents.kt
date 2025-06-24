package com.example.collegeapplication.fmc

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.collegeapplication.ui.theme.CollegeApplicationTheme
import com.example.collegeapplication.ui.theme.GenderTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FMCTopAppBar(
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
fun CycleBox(modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf("") }
    var cycleResult by remember { mutableStateOf<List<Int>?>(null) }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "Calculate Your Cycle",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Input field
        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it.filter { char -> char.isDigit() }
                showError = false
            },
            label = { Text("Enter marks (20-100)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    val marks = inputText.toIntOrNull()
                    if (marks != null) {
                        cycleResult = calculateCycle(marks)
                        showError = cycleResult == null
                    } else {
                        showError = true
                    }
                }
            ),
            isError = showError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (showError) {
            Text(
                text = "Please enter a valid number between 20 and 100",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }

        // Calculate Button
        Button(
            onClick = {
                val marks = inputText.toIntOrNull()
                if (marks != null) {
                    cycleResult = calculateCycle(marks)
                    showError = cycleResult == null
                } else {
                    showError = true
                }
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp)
        ) {
            Text("Calculate")
        }

        // Result Display
        cycleResult?.let { result ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Cycle Distribution",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Display the weights with labels
                    val componentLabels = listOf("Theory", "Lab", "Assignment", "Quiz", "Project")
                    for (i in 0 until 5) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = componentLabels[i],
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "${result[i]}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    // Total and original marks
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${result[5]}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Original Marks",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${result[6]}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BoxComponent() {
    var inputText by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = inputText,
        onValueChange = {
            inputText = it.filter { char -> char.isDigit() }
            showError = false
        },
        label = { Text("Enter marks (20-100)") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                calculateAverage(
                    incomingMarks = inputText
                )
            }
        ),
        isError = showError,
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Cycle1(
    modifier: Modifier = Modifier,
    startIndex: Int = 1,
    linkedStartIndex: Boolean = false,
    onEndIndexChange: (Int) -> Unit = {},
    onMarksListChange: (MutableList<String>) -> Unit = {}
) {
    var endIndex by remember { mutableStateOf(startIndex) }
    var marksList by remember { mutableStateOf(mutableListOf<String>()) }
    var expanded by remember { mutableStateOf(false) }

    // Theme colors for better visual appearance
    val accentColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.surfaceVariant
    val textColor = MaterialTheme.colorScheme.onSurface

    // Calculate how many boxes to show
    val numBoxes = if (linkedStartIndex) {
        // For linked cycle: endIndex - startIndex + 1
        maxOf(0, endIndex - startIndex + 1)
    } else {
        // For first cycle: just use endIndex
        endIndex
    }

    // Update marks list when number of boxes changes
    if (marksList.size != numBoxes) {
        marksList = MutableList(numBoxes) { "" }
        onMarksListChange(marksList)
    }

    // Notify parent when end index changes
    if (!linkedStartIndex && endIndex > 0) {
        onEndIndexChange(endIndex)
    }

    // When marks list changes, notify parent
    LaunchedEffect(marksList) {
        onMarksListChange(marksList)
    }

    Card(
        modifier = modifier
            .padding(8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Title
            Text(
                text = "Cycle ${if (linkedStartIndex) "2" else "1"}",
                style = MaterialTheme.typography.titleMedium,
                color = accentColor,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 12.dp).fillMaxWidth()
            )

            Row {
                // Start index field - either fixed at 1 or linked to previous cycle
                OutlinedTextField(
                    value = startIndex.toString(),
                    onValueChange = { /* No changes allowed */ },
                    label = { Text("Start Index") },
                    readOnly = true,
                    enabled = false,
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = textColor.copy(alpha = 0.7f),
                        disabledBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        disabledLabelColor = MaterialTheme.colorScheme.outline
                    ),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // End index dropdown with enhanced visuals
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = endIndex.toString(),
                        onValueChange = { /* No changes allowed */ },
                        label = { Text("End Index") },
                        readOnly = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        ),
                        // Add padding to prevent value from being hidden by the dropdown icon
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            textAlign = TextAlign.Start
                        ),
                        trailingIcon = {
                            IconButton(
                                onClick = { expanded = !expanded },
                                modifier = Modifier.padding(start = 8.dp) // Add padding to separate icon from text
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Select End Index",
                                    tint = accentColor
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Ensure the dropdown is positioned correctly and visible
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .align(Alignment.TopStart)
                            .width(with(LocalDensity.current) { 120.dp })
                    ) {
                        // For linked cycle, ensure end index is greater than start index
                        val minValue = if (linkedStartIndex) startIndex else 1
                        val maxValue = minOf(startIndex + 19, 30) // Limit to reasonable range

                        (minValue..maxValue).forEach { index ->
                            DropdownMenuItem(
                                text = { Text(index.toString()) },
                                onClick = {
                                    endIndex = index
                                    expanded = false
                                    // Ensure the parent component is notified about the change
                                    if (!linkedStartIndex) {
                                        onEndIndexChange(index)
                                    }
                                },
                                colors = MenuDefaults.itemColors(
                                    textColor = textColor,
                                    leadingIconColor = accentColor
                                )
                            )
                        }
                    }
                }
            }

            // Divider with animation
            if (numBoxes > 0) {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth()
                        .height(1.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }

            // Display the mark input boxes with better styling
            AnimatedVisibility(
                visible = numBoxes > 0,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    for (i in 0 until numBoxes) {
                        val boxNumber = if (linkedStartIndex) i + startIndex else i + 1

                        // Use a separate mutableState for each input field
                        var currentInput by remember { mutableStateOf(marksList.getOrElse(i) { "" }) }

                        OutlinedTextField(
                            value = currentInput,
                            onValueChange = { newValue ->
                                // Only filter digits during typing, don't constrain yet
                                currentInput = newValue.filter { it.isDigit() }

                                // Update the marks list with the raw input
                                val newList = marksList.toMutableList()
                                if (i < newList.size) {
                                    newList[i] = currentInput
                                } else {
                                    newList.add(currentInput)
                                }
                                marksList = newList
                            },
                            label = { Text("Mark $boxNumber") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    // Apply constraints when Done is pressed
                                    val numericValue = currentInput.toIntOrNull()
                                    if (numericValue != null && currentInput.isNotEmpty()) {
                                        val constrainedValue = when {
                                            numericValue < 20 -> "20"
                                            numericValue > 100 -> "100"
                                            else -> currentInput
                                        }

                                        // Update both the displayed input and the stored value
                                        currentInput = constrainedValue
                                        val newList = marksList.toMutableList()
                                        if (i < newList.size) {
                                            newList[i] = constrainedValue
                                        } else {
                                            newList.add(constrainedValue)
                                        }
                                        marksList = newList
                                    }
                                }
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = accentColor,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                focusedLabelColor = accentColor
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .onFocusChanged { focusState ->
                                    // Apply constraints when focus is lost
                                    if (!focusState.isFocused) {
                                        val numericValue = currentInput.toIntOrNull()
                                        if (numericValue != null && currentInput.isNotEmpty()) {
                                            val constrainedValue = when {
                                                numericValue < 20 -> "20"
                                                numericValue > 100 -> "100"
                                                else -> currentInput
                                            }

                                            // Update both the displayed input and the stored value
                                            currentInput = constrainedValue
                                            val newList = marksList.toMutableList()
                                            if (i < newList.size) {
                                                newList[i] = constrainedValue
                                            } else {
                                                newList.add(constrainedValue)
                                            }
                                            marksList = newList
                                        }
                                    }
                                }
                        )
                    }
                }
            }

            // Calculate and display average with enhanced styling
            val validMarks = marksList
                .filter { it.isNotEmpty() }
                .mapNotNull { it.toIntOrNull() }

            if (validMarks.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = accentColor.copy(alpha = 0.1f)
                    ),
                    border = BorderStroke(1.dp, accentColor.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Average",
                            style = MaterialTheme.typography.titleSmall,
                            color = textColor
                        )

                        Text(
                            text = "${String.format("%.2f", validMarks.average())}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        )
                    }
                }
            }
        }
    }
}


// Helper function to calculate average of multiple values
private fun calculateAverage(incomingMarks: String): Double {
    var listForAverage = mutableListOf(0)
    listForAverage = (listForAverage + incomingMarks.toInt()).toMutableList()
    return listForAverage.average()
}

// Helper function to calculate cycle distribution
private fun calculateCycle(marksInput: Int): List<Int>? {
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
    if (tempDiff >= 6) {
        l[1]--; tempDiff -= 6
    }
    if (tempDiff >= 4) {
        l[4]--; tempDiff -= 4
    }
    if (tempDiff in 2..4) {
        l[2]--; tempDiff -= 2
    }
    if (tempDiff >= 6) {
        l[0]--; tempDiff -= 6
    }
    if (tempDiff >= 2) {
        l[3]--; tempDiff -= 2
    }

    // Calculate weighted scores
    val w = IntArray(5) { weights[it] * l[it] }
    val total = w.sum()
    return w.toList() + total + marks
}

// Helper function to calculate average from a list of integers
private fun calculateAverageFromList(marks: List<Int>): Double {
    return if (marks.isNotEmpty()) {
        marks.average()
    } else {
        0.0
    }
}

@Composable
fun CycleRow(modifier: Modifier = Modifier) {
    // Shared state between cycles
    var firstCycleEndIndex by remember { mutableStateOf(1) }
    var marksList1 by remember { mutableStateOf(mutableListOf<String>()) }
    var marksList2 by remember { mutableStateOf(mutableListOf<String>()) }

    // State for cycle results
    var firstCycleResult by remember { mutableStateOf<List<Int>?>(null) }
    var secondCycleResult by remember { mutableStateOf<List<Int>?>(null) }

    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row {

            Cycle1(
                modifier = Modifier.fillMaxWidth(),
                startIndex = 1,
                onEndIndexChange = { newEndIndex ->
                    firstCycleEndIndex = newEndIndex
                },
                onMarksListChange = { newMarksList ->
                    marksList1 = newMarksList

                    // Calculate average from the marks list
                    val validMarks = marksList1
                        .filter { it.isNotEmpty() }
                        .mapNotNull { it.toIntOrNull() }

                    if (validMarks.isNotEmpty()) {
                        // Use the average to calculate cycle result
                        val average = validMarks.average().toInt()
                        firstCycleResult = calculateCycle(average)
                    } else {
                        firstCycleResult = null
                    }
                }
            )
            Cycle1(
                modifier = Modifier.fillMaxWidth(),
                startIndex = firstCycleEndIndex + 1,
                linkedStartIndex = true,
                onMarksListChange = { newMarksList ->
                    marksList2 = newMarksList

                    // Calculate average from the marks list
                    val validMarks = marksList2
                        .filter { it.isNotEmpty() }
                        .mapNotNull { it.toIntOrNull() }

                    if (validMarks.isNotEmpty()) {
                        // Use the average to calculate cycle result
                        val average = validMarks.average().toInt()
                        secondCycleResult = calculateCycle(average)
                    } else {
                        secondCycleResult = null
                    }
                }
            )
        }
        // First Cycle component - This collects marks and calculates average

        // Second Cycle component

        // Display tables below the input sections
        Spacer(modifier = Modifier.height(16.dp))

        // First Cycle Table
        firstCycleResult?.let { result ->
            CycleTable(
                cycleNumber = 1,
                cycleResult = result,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Second Cycle Table
        secondCycleResult?.let { result ->
            CycleTable(
                cycleNumber = 2,
                cycleResult = result,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FMCPreview() {
    CollegeApplicationTheme {
//        FMCTopAppBar(
//            gender = GenderTheme.BOY
//        ) { }
        CycleRow()
    }
}