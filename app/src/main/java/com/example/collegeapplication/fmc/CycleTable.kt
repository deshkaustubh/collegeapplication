package com.example.collegeapplication.fmc

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A table component to display cycle distribution with check marks
 * @param cycleNumber The cycle number (1 or 2)
 * @param cycleResult The result from calculateCycle function
 */
@Composable
fun CycleTable(
    cycleNumber: Int,
    cycleResult: List<Int>,
    modifier: Modifier = Modifier
) {
    val componentLabels = listOf("Theory", "Lab", "Assignment", "Quiz", "Project")
    val borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
    val headerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    val cellColor = MaterialTheme.colorScheme.surface
    val accentColor = MaterialTheme.colorScheme.primary

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = "Cycle $cycleNumber",
                style = MaterialTheme.typography.titleMedium,
                color = accentColor,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Table Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, borderColor))
            ) {
                // Component column
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .background(headerColor)
                        .border(BorderStroke(1.dp, borderColor))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Component",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Points columns (1-5)
                for (i in 1..5) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(headerColor)
                            .border(BorderStroke(1.dp, borderColor))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = i.toString(),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Value column
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(headerColor)
                        .border(BorderStroke(1.dp, borderColor))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Value",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Table Rows
            for (rowIndex in 0 until 5) {
                val value = cycleResult[rowIndex]
                val pointValue = value / componentLabels.size

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(1.dp, borderColor))
                ) {
                    // Component label
                    Box(
                        modifier = Modifier
                            .weight(2f)
                            .background(cellColor)
                            .border(BorderStroke(1.dp, borderColor))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = componentLabels[rowIndex],
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Points cells (1-5)
                    for (colIndex in 1..5) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(cellColor)
                                .border(BorderStroke(1.dp, borderColor))
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // The logic for placing checks:
                            // If the point value is >= column index, show a check
                            if (6 - colIndex <= value / componentLabels.size) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Check",
                                    tint = accentColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    // Value column (the actual numeric value)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(cellColor)
                            .border(BorderStroke(1.dp, borderColor))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = value.toString(),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Total and Original Marks row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total: ${cycleResult[5]}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Original: ${cycleResult[6]}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
