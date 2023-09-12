package com.uvg.profitpulse

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PopUp : ViewModel(){

    var isDialogShown by mutableStateOf(false)
        private set

    fun agregar(){
        isDialogShown = true
    }

    fun onDimissDialog(){
        isDialogShown = false
    }

}