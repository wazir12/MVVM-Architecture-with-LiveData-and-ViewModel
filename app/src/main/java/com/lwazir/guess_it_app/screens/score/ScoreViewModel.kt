package com.lwazir.guess_it_app.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore : Int) :ViewModel() {

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    private var _playAgain = MutableLiveData<Boolean>()

    //external
    val playAgain: LiveData<Boolean>
        get() = _playAgain
    // The current score
    private var _score = MutableLiveData<Int>()

    //external
    val final_score: LiveData<Int>
        get() = _score
    init {
            Log.i("ScoreViewModel","The final score is ${finalScore}")
        _score.value = finalScore
    }
    //TODO: private val _eventPlayAgain = MutableLiveData<Boolean>()
    //    val eventPlayAgain: LiveData<Boolean>
    //        get() = _eventPlayAgain
    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }

}