package com.example.and2.homework.and.homework.s01.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.and2.homework.and.homework.s01.databinding.FragmentEditPostBinding
import com.example.and2.homework.and.homework.s01.util.AndroidUtils
import com.example.and2.homework.and.homework.s01.viewmodel.PostViewModel

class EditPostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
    private lateinit var binding: FragmentEditPostBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditPostBinding.inflate(layoutInflater, container, false)

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

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        applyInsets(binding.root)
//
//        AndroidUtils.showKeyboard(binding.edit)
//
//        intent?.let {
//            if (it.action != Intent.ACTION_SEND) {
//                return@let
//            }
//            val text = it.getStringExtra(Intent.EXTRA_TEXT)
//            if (text.isNullOrBlank()) {
//                Snackbar.make(
//                    binding.root, R.string.error_empty_content,
//                    BaseTransientBottomBar.LENGTH_INDEFINITE
//                )
//                    .setAction(android.R.string.ok) {
//                        finish()
//                    }.show()
//                return@let
//            }
//            binding.edit.setText(text)
//
//            binding.ok.setOnClickListener {
//                if (binding.edit.text.isNullOrBlank()) {
//                    setResult(Activity.RESULT_CANCELED)
//                } else {
//                    val intent = Intent()
//                    val content = binding.edit.text.toString()
//                    intent.putExtra(Intent.EXTRA_TEXT, content)
//                    setResult(Activity.RESULT_OK, intent)
//                }
//                finish()
//            }
//        }
//    }
}