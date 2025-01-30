package com.example.voicetotext.data

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VoiceToTextParser(
    private val context: Context
): RecognitionListener {

    private val _state = MutableStateFlow(SpeechToTextState())
    val state = _state.asStateFlow()

    val recognizer = SpeechRecognizer.createSpeechRecognizer(context)

    fun startListening() {
        if(!SpeechRecognizer.isRecognitionAvailable(context)) {
            _state.update { stt->
                stt.copy(
                    error = "Speech recognition not available"
                )
            }
        }
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM,
                RecognizerIntent.EXTRA_LANGUAGE_MODEL
            )
        }
        recognizer.setRecognitionListener(this)
        recognizer.startListening(intent)
        _state.update { stt->
            stt.copy(
                isListening = true
            )
        }
        Log.d("STT", "Start listening() called.")
    }
    fun stopListening() {
        _state.update { stt->
            stt.copy(
                isListening = false
            )
        }
        recognizer.stopListening()
        Log.d("STT", "Stop Listening() called.")
    }

    override fun onReadyForSpeech(p0: Bundle?) {
        _state.update { it.copy(error = null) }
    }

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(p0: Float) = Unit

    override fun onBufferReceived(p0: ByteArray?) = Unit

    override fun onEndOfSpeech() {
        _state.update { stt->
            stt.copy(
                isListening = false,
            )
        }
        Log.d("STT", "END OF SPEECH CALLED")
    }

    override fun onError(error: Int) {
        if (error == SpeechRecognizer.ERROR_CLIENT || error == SpeechRecognizer.ERROR_RECOGNIZER_BUSY) {
            return // Ignore certain errors to prevent abrupt stopping
        }
        _state.update { stt ->
            stt.copy(
                isListening = false, // Ensure listening stops only for major errors
                error = "Error code: $error"
            )
        }
    }

    override fun onResults(results: Bundle?) {
        val res = results
            ?.getStringArray(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let{result->
                _state.update { stt->
                    stt.copy(
                        listenedText = result
                    )
                }
                result
            }
        Log.d("STT", res ?: "empty")
    }

    override fun onPartialResults(results: Bundle?) = Unit

    override fun onEvent(p0: Int, p1: Bundle?) = Unit

}

data class SpeechToTextState(
    val isListening: Boolean = false,
    val error: String? = null,
    val listenedText: String = ""
)