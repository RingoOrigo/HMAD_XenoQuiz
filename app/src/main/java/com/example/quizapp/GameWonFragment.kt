package com.example.quizapp

import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.quizapp.databinding.FragmentGameWonBinding

class GameWonFragment : Fragment() {
    private var _binding: FragmentGameWonBinding? = null
    private val binding get() = _binding!!

    lateinit var media: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameWonBinding.inflate(layoutInflater, container, false)

        setHasOptionsMenu(true)

        media = MediaPlayer.create(context, R.raw.victory)
        media.isLooping = true
        media.start()

        binding.pauseButton.setOnClickListener {
            if (media.isPlaying) {
                media.pause()
                binding.pauseButton.setImageResource(R.drawable.ic_play)
            }
            else {
                media.start()
                binding.pauseButton.setImageResource(R.drawable.ic_pause)
            }
        }

        val resumeRewindListener : View.OnClickListener = View.OnClickListener{ view ->
            when(view) {
                binding.rewindButton -> media.seekTo(media.currentPosition - 10000)
                binding.fastForwardButton -> media.seekTo(media.currentPosition + 10000)
            }
        }

        binding.rewindButton.setOnClickListener(resumeRewindListener)
        binding.fastForwardButton.setOnClickListener(resumeRewindListener)

        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        if (args.incorrectAttempts == 1) {
            binding.incorrectAttemptsView.text = "You had ${args.incorrectAttempts} incorrect attempt."
        }
        else binding.incorrectAttemptsView.text = "You had ${args.incorrectAttempts} incorrect attempts"

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

    override fun onStop() {
        super.onStop()
        media.release()
    }

}