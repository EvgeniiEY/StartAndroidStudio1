package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.EditPostFragment.Companion.edit
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.databinding.FragmentEditPostBinding
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.LongArg
import ru.netology.nmedia.utils.StringArg
import ru.netology.nmedia.utils.Utils
import ru.netology.nmedia.viewModel.PostViewModel


class PostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
        var str = ""
        repeat(50) {
            str += "$it\n"

        }
        val postId = arguments?.longArg ?: -1
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find {
                it.id == postId
            } ?: return@observe

            with(binding.postContent) {
                author.text = post.author
                content.text = post.content
                published.text = post.published
                likesButton.text = post.likes.toString()
                repostsButton.text = post.share.toString()
                likesButton.isChecked = post.likedByMe
                likesButton.text = Utils.reductionInNumbers(post.likes)
                repostsButton.text = Utils.reductionInNumbers(post.share)
                likesButton.setOnClickListener {
                    viewModel.likeById(post.id)
                }
                repostsButton.setOnClickListener {

                    viewModel.shareById(post.id)
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        data = Uri.parse("link to an image")
                        type = "text/plain"
                    }

                    val chooser = Intent.createChooser(intent, getString(R.string.share_post))
                    startActivity(chooser)



                }
                threeDotButton.setOnClickListener {
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.post_options)

                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {

                                R.id.post_remove -> {

                                    viewModel.removeById(post.id)
                                    findNavController().navigateUp()
                                    true

                                }
                                R.id.post_edit -> {

                                    viewModel.edit(post)


                                    true


                                }
                                else -> false
                            }
                        }
                    }.show()
                }






                playVideoButton.setOnClickListener {
                    if (post.video.isNullOrBlank()) {
                        Toast.makeText(
                            context,
                            "Нечего проигрывать!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                    val videoIntent =
                        Intent.createChooser(intent, getString(R.string.chooser_video_player))
                    startActivity(videoIntent)


                }


            }
            viewModel.edited.observe(viewLifecycleOwner) edited@ { editingPost ->
                if (editingPost.id == 0L) {
                    return@edited
                }

                findNavController().navigate(R.id.action_postFragment_to_editPostFragment,
                    Bundle().apply { edit = editingPost.content })
            }




        }



        return binding.root
    }

    companion object {

        var Bundle.longArg: Long by LongArg

    }


}