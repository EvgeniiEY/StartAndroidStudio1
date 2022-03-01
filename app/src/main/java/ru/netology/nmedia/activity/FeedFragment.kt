package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.netology.nmedia.adapter.PostCallBack
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.EditPostFragment.Companion.edit
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewModel.PostViewModel

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapter = PostsAdapter(object : PostCallBack {

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
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

            override fun remove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun edit(post: Post) {
                viewModel.edit(post)

            }

            override fun playVideo(post: Post) {
                if (post.video.isNullOrBlank()) {
                    Toast.makeText(
                        context,
                        "Нечего проигрывать!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                val videoIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_video_player))
                startActivity(videoIntent)



            }



        })
        binding.mainList.adapter = adapter
        binding.mainList.itemAnimator = null

        viewModel.data.observe(viewLifecycleOwner) {  posts ->
                adapter.submitList(posts)
            }


        binding.add.setOnClickListener() {

            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)

        }

        binding.mainList.setOnClickListener() { post ->


            findNavController().navigate(R.id.action_feedFragment_to_postFragment, Bundle().apply
            { edit = post.content  })


        }





        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id == 0L) {
                return@observe
            }

            findNavController().navigate(R.id.action_feedFragment_to_editPostFragment, Bundle().apply
            { edit = post.content })
        }





        return binding.root
    }
}



