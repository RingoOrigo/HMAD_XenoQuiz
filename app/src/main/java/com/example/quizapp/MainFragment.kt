package com.example.quizapp

import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.quizapp.databinding.FragmentMainBinding

const val KEY_CURRENT_QUESTION_NUMBER = "current_question_number"
const val HAS_CHEATED_THIS_QUESTION = "has_cheated"

class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    var correctQuestions: Int = 0
    var incorrectAttempts: Int = 0
    var currentQuestionIndex : Int = 0

    val questions = listOf(
        Question(R.string.first_question, true, false),
        Question(R.string.second_question, false, false),
        Question(R.string.third_question, false, false),
        Question(R.string.fourth_question, true, false),
        Question(R.string.fifth_question, true, false)
    )

    lateinit var media : MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentMainBinding.inflate(inflater, container, false)
        val rootView = binding.root

        setHasOptionsMenu(true)

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_CURRENT_QUESTION_NUMBER)
            questions[currentQuestionIndex].cheated = savedInstanceState.getBoolean(HAS_CHEATED_THIS_QUESTION)
        }

        binding.questionTextView.text = getString(questions[currentQuestionIndex].stringID)

        //Set true and false button listeners
        binding.trueButton.setOnClickListener{checkAnswer(true)} //Calls checkAnswer, passing in true boolean, falseButton passes in false instead..
        binding.falseButton.setOnClickListener{checkAnswer(false)}

        //Set next button listener
        binding.advanceQuestionButton.setOnClickListener{setQuestion(currentQuestionIndex + 1)}
        binding.lastQuestionButton.setOnClickListener{setQuestion(currentQuestionIndex - 1)}

        binding.questionTextView.setOnClickListener{setQuestion(currentQuestionIndex + 1)}

        binding.cheatButton.setOnClickListener {
            binding.root.findNavController().navigate(MainFragmentDirections.actionMainFragmentToCheatFragment(questions[currentQuestionIndex].correctAnswer))
        }


        setFragmentResultListener("HAS_CHEATED_KEY") {requestKey: String, bundle: Bundle ->
            questions[currentQuestionIndex].cheated = bundle.getBoolean("CHEAT_KEY")
        }
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI
            .onNavDestinationSelected(item, requireView().findNavController()) ||
            super.onOptionsItemSelected(item)
    }

    fun checkAnswer(userAnswer: Boolean) {
        if (questions[currentQuestionIndex].cheated) {
            Toast.makeText(activity, "This future shouldn't belong to you", Toast.LENGTH_SHORT).show()
            media = MediaPlayer.create(context, R.raw.right)
        }
        else if (userAnswer == questions[currentQuestionIndex].correctAnswer) {
            Toast.makeText(activity, R.string.correct, Toast.LENGTH_SHORT).show()
            correctQuestions++

            media = MediaPlayer.create(context, R.raw.right)
        }
        else {
            Toast.makeText(activity, R.string.incorrect, Toast.LENGTH_SHORT).show()
            incorrectAttempts++
            media = MediaPlayer.create(context, R.raw.wrong)
        }

        media.start()

        when (correctQuestions) {
            3 -> {binding.root.findNavController().navigate(MainFragmentDirections.actionMainFragmentToGameWonFragment(incorrectAttempts))
                    media.stop()}
        }
    }

    fun setQuestion(index: Int) {
        //Check if next question will cause a crash (Do not let the current index get too high)
        if (index >= questions.size) {
            binding.questionTextView.text = getString(questions[0].stringID)
            currentQuestionIndex = 0
        }
        else if (index < 0) {
            binding.questionTextView.text = getString(questions[questions.size - 1].stringID)
            currentQuestionIndex = questions.size - 1
        }
        else {
            binding.questionTextView.text = getString(questions[index].stringID)//Sets the question text to the current question within the sequence.
            currentQuestionIndex++
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_CURRENT_QUESTION_NUMBER, currentQuestionIndex)
        savedInstanceState.putBoolean(HAS_CHEATED_THIS_QUESTION, questions[currentQuestionIndex].cheated)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class Question(val stringID: Int, val correctAnswer: Boolean, var cheated: Boolean) {}