package com.app.acronyms.common

import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.annotation.CheckResult
import androidx.annotation.RequiresApi
import androidx.core.os.postDelayed
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

fun EditText.debounce(delay: Long, action: (Editable?) -> Unit) {
    doAfterTextChanged { text ->
        var counter = getTag(id) as? Int ?: 0
        handler.removeCallbacksAndMessages(counter)
        handler.postDelayed(delay, ++counter) { action(text) }
        setTag(id, counter)
    }
}

 fun EditText.clear() {
    this.setText("")
}
