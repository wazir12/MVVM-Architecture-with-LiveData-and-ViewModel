package com.lwazir.guess_it_app.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    private lateinit var viewModel: GameViewModel


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
        viewModel.word.observe(viewLifecycleOwner, Observer { wordText ->
            binding.wordText.text = wordText.toString()
        })
        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })
        viewModel.gameFinished.observe(viewLifecycleOwner, Observer { hasFinished ->
            if(hasFinished){
                gameFinished()
                viewModel.onGameFinishedComplete()
            }
        })
        viewModel.currentTime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)
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



    /** Methods for updating the UI **/

    private fun updateWordText() {
        binding.wordText.text = viewModel.word.value

    }

    private fun updateScoreText() {
        binding.scoreText.text =viewModel.score.toString()
    }
}