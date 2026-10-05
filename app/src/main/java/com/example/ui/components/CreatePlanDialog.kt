package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CreatePlanDialog(
    onDismiss: () -> Unit,
    onCreatePlan: (title: String, sector: String, commodities: String, author: String, institution: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var sector by remember { mutableStateOf("Cereal Grains & Oilseeds") }
    var commodities by remember { mutableStateOf("Corn, Soybeans, Wheat") }
    var author by remember { mutableStateOf("Agribusiness Analytics Fellow") }
    var institution by remember { mutableStateOf("Agricultural Economics Institute") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Create New Agribusiness Plan",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Plan / Project Title") },
                    placeholder = { Text("e.g. Grain Export Price Elasticity Plan") },
                    modifier = Modifier.fillMaxWidth().testTag("input_new_plan_title")
                )

                OutlinedTextField(
                    value = sector,
                    onValueChange = { sector = it },
                    label = { Text("Agribusiness Sector") },
                    placeholder = { Text("e.g. Grains, Cash Crops, Fertilizers") },
                    modifier = Modifier.fillMaxWidth().testTag("input_new_plan_sector")
                )

                OutlinedTextField(
                    value = commodities,
                    onValueChange = { commodities = it },
                    label = { Text("Target Commodities") },
                    placeholder = { Text("e.g. Corn, Soybeans, Coffee, Fertilizer") },
                    modifier = Modifier.fillMaxWidth().testTag("input_new_plan_commodities")
                )

                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Student Researcher / Author") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = institution,
                    onValueChange = { institution = it },
                    label = { Text("Institution / University") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onCreatePlan(
                        title.ifBlank { "New Agribusiness Data Analysis Plan" },
                        sector,
                        commodities,
                        author,
                        institution
                    )
                    onDismiss()
                },
                modifier = Modifier.testTag("btn_confirm_create_plan")
            ) {
                Text("Create Plan")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
