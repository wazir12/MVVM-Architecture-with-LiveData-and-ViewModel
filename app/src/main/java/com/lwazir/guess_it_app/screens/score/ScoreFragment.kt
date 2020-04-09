package com.lwazir.guess_it_app.screens.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs



import com.lwazir.guess_it_app.R
import com.lwazir.guess_it_app.databinding.FragmentScoreBinding

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {
    private lateinit var  scoreViewModel : ScoreViewModel
    private  lateinit var  viewModelFactory: ScoreViewModelFactory
private lateinit var  binding : FragmentScoreBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
            binding= DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_score,
            container,
            false
        )

        //getting instance of scoreViewModelFactory
        viewModelFactory = ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(arguments!!).score)
        scoreViewModel = ViewModelProvider(this,viewModelFactory).get(ScoreViewModel::class.java)
//TODO: Pass the ScoreViewModel into the data binding and remove OnClickListener setup for playAgainButton.
        binding.scoreViewModel = scoreViewModel
        // Get args using by navArgs property delegate
        scoreViewModel.final_score.observe(viewLifecycleOwner, Observer{finalScore->
            binding.scoreText.text = finalScore.toString()
        })
        binding.playAgainButton.setOnClickListener { scoreViewModel.onPlayAgain() }

        // TODO: Observe the eventPlayAgain value and according navigate to title page
        scoreViewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { playAgain ->
            if (playAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                scoreViewModel.onPlayAgainComplete()
            }
        })
        //val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
        // val args = ScoreFragmentArgs.fromBundle(arguments!!)
         //binding.scoreText.text = args.score.toString()


        return binding.root
    }

    private fun onPlayAgain() {
       findNavController().navigate(ScoreFragmentDirections.actionRestart())
    }
}
