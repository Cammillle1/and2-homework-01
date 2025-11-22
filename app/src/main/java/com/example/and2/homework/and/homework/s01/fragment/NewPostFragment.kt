package com.example.and2.homework.and.homework.s01.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.and2.homework.and.homework.s01.databinding.FragmentNewPostBinding
import com.example.and2.homework.and.homework.s01.util.AndroidUtils
import com.example.and2.homework.and.homework.s01.viewmodel.PostViewModel
import kotlin.getValue

class NewPostFragment : Fragment() {
    private lateinit var binding: FragmentNewPostBinding
    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPostBinding.inflate(layoutInflater, container, false)
        arguments?.textArg?.let(binding.edit::setText)

        binding.ok.setOnClickListener {
            viewModel.savePost(binding.edit.text.toString())
            AndroidUtils.showKeyboard(requireView())
            findNavController().navigateUp()
        }
        return binding.root
    }

    companion object {
        private const val KEY_TEXT = "text"
        var Bundle.textArg: String?
            set(value) = putString(KEY_TEXT, value)
            get() = getString(KEY_TEXT)
    }


}