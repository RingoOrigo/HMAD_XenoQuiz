package com.example.quizapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class QuizViewModel {
    private val  questions = listOf(
        Question(R.string.first_question, true, false),
        Question(R.string.second_question, false, false),
        Question(R.string.third_question, false, false),
        Question(R.string.fourth_question, true, false),
        Question(R.string.fifth_question, true, false))

    private val _currentQuestionIndex = MutableLiveData(0)
    val currentQuestionIndex : LiveData<Int>
        get() = _currentQuestionIndex

    private var _correctQuestions = 0
    val correctQuestions : Int
        get() = _correctQuestions

    private var _incorrectAttempts = 0
    val incorrectAttempts : Int
        get() = _incorrectAttempts


    private val _gameWon = MutableLiveData(false)
    val gameWon : LiveData<Boolean>
            get() = _gameWon

    //Getters

    val currentCorrectAnswer : Boolean
        get() = questions[currentQuestionIndex.value ?: 0].correctAnswer

    val currentQuestionText : String
        get() = questions[currentQuestionIndex.value ?: 0].stringID.toString()

    val hasCheated : Boolean
        get() = questions[currentQuestionIndex.value ?: 0].cheated
}