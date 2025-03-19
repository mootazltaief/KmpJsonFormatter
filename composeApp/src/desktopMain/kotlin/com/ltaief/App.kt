package com.ltaief

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun App() {
    val viewModel: JsonFormatterViewModel = viewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "JSON Formatter",
                style = MaterialTheme.typography.h4
            )

            Text("Input JSON:")
            OutlinedTextField(
                value = state.inputJson,
                onValueChange = viewModel::updateInputJson,
                modifier = Modifier.fillMaxWidth().height(200.dp),
                maxLines = 20,
                textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = viewModel::formatJson) {
                    Text("Format JSON")
                }

                Button(onClick = viewModel::saveFormattedJson) {
                    Text("Save Formatted JSON")
                }
            }

            state.error?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            if (state.formattedJson.isNotEmpty()) {
                Text("Formatted JSON:")
                OutlinedTextField(
                    value = state.formattedJson,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    maxLines = 50,
                    textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace)
                )
            }
        }
    }
}