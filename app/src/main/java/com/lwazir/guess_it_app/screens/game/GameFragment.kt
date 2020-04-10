package com.lwazir.guess_it_app.screens.game

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController

import com.lwazir.guess_it_app.R
import com.lwazir.guess_it_app.databinding.FragmentGameBinding
import com.lwazir.guess_it_app.screens.score.ScoreViewModel
import com.lwazir.guess_it_app.screens.score.ScoreViewModelFactory

/**
 * Fragment where the game is played
 */

class GameFragment : Fragment() {
    private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
    private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
    private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
    private val NO_BUZZ_PATTERN = longArrayOf(0)


     private lateinit var viewModel :  GameViewModel


    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )
        //getting reference to game view model created by jetpack lifecycle library
        Log.i("GameFragment","GameViewModel called")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
//TODO: Pass the GameViewModel into the data binding:
        binding.gameViewModel = viewModel
//TODO: Remove the onClickListeners
        //binding.correctButton.setOnClickListener { viewModel.onCorrect()}
      //  binding.skipButton.setOnClickListener {viewModel.onSkip() }
        //TODO:Call binding.setLifecycleOwner to make the data binding lifecycle aware
        binding.setLifecycleOwner (this)
        //TODO: Remove the score and word observers:
        /*viewModel.word.observe(viewLifecycleOwner, Observer { wordText ->
            binding.wordText.text = wordText.toString()
        })
        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })**/
        viewModel.gameFinished.observe(viewLifecycleOwner, Observer { hasFinished ->
            if(hasFinished){
                gameFinished()
                buzz(GAME_OVER_BUZZ_PATTERN)
                viewModel.onGameFinishedComplete()
            }
        })
       viewModel.currentTime.observe(viewLifecycleOwner, Observer { newTime ->
            if(newTime < 5000)
            {
                buzz(PANIC_BUZZ_PATTERN)
            }
        })
        return binding.root

    }



    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore().setScore(viewModel.score.value ?: 0)

        findNavController(this).navigate(action)
    }
    private fun buzz(pattern: LongArray) {
 val buzzer = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        buzzer?.let {



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }

        }
    }




}