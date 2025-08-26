package hu.ait.aidemo.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class GenAIViewModel: ViewModel() {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = "AIzaSyDq6ppwq4Kk8HlNuV2QDcOfK3sKxsGJpLA",
        generationConfig = generationConfig {
            temperature = 0.7f
            topP = 0.9f
            topK = 32
            maxOutputTokens = 4096
        },
        safetySettings = listOf(
            SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE)
        )
    )
    private val _textGenerationResult = MutableStateFlow<String?>(null)
    val textGenerationResult = _textGenerationResult.asStateFlow()

    fun generateStory(prompt: String = "Tell me a joke") {
        _textGenerationResult.value = "Generating..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val systemPrompt = "You are an assistant. Who always answers in 1-2 sentences."
                val finalPrompt = systemPrompt +
                        "Return only one answer, don't generate any other descriptions or text." +
                        prompt
                val result = generativeModel.generateContent(finalPrompt)
                _textGenerationResult.value = result.text
            } catch (e: Exception) {
                _textGenerationResult.value = "Error: ${e.message}"
            }
        }
    }
}