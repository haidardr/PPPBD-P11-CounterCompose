package com.example.countercompose

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel

class CounterViewModel(
    application: Application
) : AndroidViewModel(application) {
    // 1. Membuka/membuat file SharedPreferences
    private val prefs = application.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )

    // 2. State counter awal dibaca dari SharedPreferences (default 0)
    private val _counter = mutableIntStateOf(
        prefs.getInt(KEY_COUNTER, 0)
    )
    val counter: State<Int> = _counter

    // 3. State nama awal dibaca dari SharedPreferences (default "")
    private val _name = mutableStateOf(
        prefs.getString(KEY_NAME, "") ?: ""
    )
    val name: State<String> = _name

    // 4. Fungsi counter yang dipanggil dari UI
    fun increment() = updateCounter(_counter.intValue + 1)
    fun decrement() = updateCounter(_counter.intValue - 1)
    fun reset() = updateCounter(0)

    // 5. Fungsi simpan nama yang dipanggil dari UI
    fun saveName(newName: String) {
        _name.value = newName
        prefs.edit { putString(KEY_NAME, newName) }
    }

    // 6. Ubah state lalu simpan ke SharedPreferences
    private fun updateCounter(newValue: Int) {
        _counter.intValue = newValue
        prefs.edit { putInt(KEY_COUNTER, newValue) }
    }

    companion object {
        private const val PREFS_NAME = "counter_prefs"
        private const val KEY_COUNTER = "counter_value"
        private const val KEY_NAME = "counter_name"
    }
}
