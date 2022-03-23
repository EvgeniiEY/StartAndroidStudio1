package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentEditPostBinding
import ru.netology.nmedia.utils.StringArg
import ru.netology.nmedia.utils.Utils
import ru.netology.nmedia.viewModel.PostViewModel

class EditPostFragment : Fragment() {

    companion object {
        var Bundle.edit: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditPostBinding.inflate(inflater, container, false)

        arguments?.edit?.let(binding.contentEditor::setText)


        binding.saveEdit.setOnClickListener {
            if (binding.contentEditor.text.isNullOrBlank()) {
                Toast.makeText(
                    activity,
                    this.getString(R.string.error_empty_content),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                viewModel.changeContent(binding.contentEditor.text.toString())
                viewModel.save()
                Utils.hideKeyboard(requireView())
                findNavController().navigateUp()
            }
        }

        binding.cancelButton.setOnClickListener {
            Utils.hideKeyboard(requireView())
            viewModel.cancel()
            findNavController().navigateUp()
        }
        return binding.root
    }
}