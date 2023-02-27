package com.example.quizapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.quizapp.databinding.FragmentCheatBinding

class CheatFragment : Fragment() {

    private var _binding : FragmentCheatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheatBinding.inflate(layoutInflater, container, false)

        setHasOptionsMenu(true)

        val args = CheatFragmentArgs.fromBundle(requireArguments())

        binding.revealAnswerButton.setOnClickListener {
            binding.answerTextView.text = args.correctAnswer.toString().capitalize()

            setFragmentResult("HAS_CHEATED_KEY", bundleOf("CHEAT_KEY" to true))
        }

        return binding.root
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

}