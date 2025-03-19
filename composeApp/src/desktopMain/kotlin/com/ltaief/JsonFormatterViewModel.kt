package com.ltaief

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

class JsonFormatterViewModel : ViewModel() {
    private val _state = MutableStateFlow(JsonFormatterState())
    val state: StateFlow<JsonFormatterState> = _state.asStateFlow()

    @OptIn(ExperimentalSerializationApi::class)
    private val jsonFormatter = Json {
        prettyPrint = true
        prettyPrintIndent = "    " // 4 spaces
    }

    fun updateInputJson(input: String) {
        _state.update { it.copy(inputJson = input) }
    }

    fun formatJson() {
        try {
            val inputJson = _state.value.inputJson.trim()
            if (inputJson.isEmpty()) {
                _state.update { it.copy(error = "Please enter JSON to format") }
                return
            }

            // Parse to ensure it's valid JSON, then format
            val parsedJson = jsonFormatter.parseToJsonElement(inputJson)
            val formattedJson = jsonFormatter.encodeToString(
                kotlinx.serialization.json.JsonElement.serializer(),
                parsedJson
            )

            _state.update {
                it.copy(
                    formattedJson = formattedJson,
                    error = null
                )
            }
        } catch (e: Exception) {
            _state.update { it.copy(error = "Invalid JSON: ${e.message}") }
        }
    }

    fun saveFormattedJson() {
        val formatted = _state.value.formattedJson
        if (formatted.isEmpty()) {
            _state.update { it.copy(error = "No formatted JSON to save") }
            return
        }

        val fileDialog = FileDialog(Frame(), "Save JSON File", FileDialog.SAVE)
        fileDialog.file = "formatted.json"
        fileDialog.isVisible = true

        val directory = fileDialog.directory
        val filename = fileDialog.file

        if (directory != null && filename != null) {
            try {
                val file = File(directory, filename)
                file.writeText(formatted)
                _state.update { it.copy(error = null) }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Failed to save file: ${e.message}") }
            }
        }
    }
}

data class JsonFormatterState(
    val inputJson: String = "",
    val formattedJson: String = "",
    val error: String? = null
)