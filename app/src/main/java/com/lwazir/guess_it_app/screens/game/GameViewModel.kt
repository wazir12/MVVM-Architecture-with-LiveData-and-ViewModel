package com.lwazir.guess_it_app.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
//TODO:different buzz pattern Long array constants:
private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)
class GameViewModel : ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }
    // The current word
    private var _currentTime = MutableLiveData<Long>()

    //external
    val currentTime: LiveData<Long>
        get() = _currentTime
//TODO: Create a new Live data called currentTimeString and use Transformation.map to transform currentTime value to suitable date format
     val currentTimeString = Transformations.map(currentTime, {time->
    DateUtils.formatElapsedTime(time)
})



    //timer
    private lateinit var timer : CountDownTimer

    // The current word
    private var _word = MutableLiveData<String>()

    //external
    val word: LiveData<String>
        get() = _word

    // T
    private var _gameFinished = MutableLiveData<Boolean>()

    //external
    val gameFinished: LiveData<Boolean>
        get() = _gameFinished

    // The current score

    // internal
    private val _score = MutableLiveData<Int>()

    //external
    val score: LiveData<Int>
        get() = _score

    // The list of words - the front of the list is the next word to guess
    lateinit var wordList: MutableList<String>
    init {
            Log.i("GameViewModel", "GameViewModel Created")
        resetList()
        nextWord()
        _score.value= 0
        _word.value =""
        timer = object :CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onFinish() {

                _gameFinished.value = true
            }

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished
            }


        }
        timer.start()
    }
    fun onGameFinishedComplete()
    {
        _gameFinished.value = false
    }

    override fun onCleared() {
        super.onCleared()
        //stop timer
        timer.cancel()
        Log.i("GameViewModel","GameViewModel Destroyed")

    }
    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }
    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
            nextWord()
            //_gameFinished.value = true
           // gameFinished()
        } else {
            _word.value = wordList.removeAt(0)
        }

    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

     fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }
    //TODO:Making an enum called BuzzType in GameViewModel
    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

}