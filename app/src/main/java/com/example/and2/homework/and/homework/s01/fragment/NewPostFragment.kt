package com.example.and2.homework.and.homework.s01.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.and2.homework.and.homework.s01.databinding.FragmentNewPostBinding
import com.example.and2.homework.and.homework.s01.util.AndroidUtils
import com.example.and2.homework.and.homework.s01.viewmodel.PostViewModel
import kotlin.getValue

class NewPostFragment : Fragment() {
    lateinit var binding: FragmentNewPostBinding
    private val viewModel by viewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewPostBinding.inflate(layoutInflater, container, false)

        binding.ok.setOnClickListener {
            viewModel.savePost(binding.edit.text.toString())

            AndroidUtils.showKeyboard(requireView())


        }


        return binding.root
    }


}