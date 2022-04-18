package ru.netology.nmedia.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import java.lang.Exception
import kotlin.random.Random


class FCMService : FirebaseMessagingService() {

    private val channelId = "Netology"
    private val action = "action"
    private val content = "content"
    private val likeChannelId = "like"
    private val newPostChannelId = "newPost"
    private val pushTokenChannelId = "pushToken"
    private val gson = Gson()

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_remote_name)
            val descriptionText = getString(R.string.channel_remote_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

        }

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        println(Gson().toJson(remoteMessage))

        remoteMessage.data[action]?.let {
            when (it) {
                likeChannelId -> handleLike(
                    gson.fromJson(
                        remoteMessage.data[content],
                        Like::class.java
                    )
                )
                newPostChannelId -> handlerNewPost(
                    gson.fromJson(
                        remoteMessage.data[content],
                        NewPost::class.java
                    )
                )
            }
        }

        try {
            remoteMessage.data["action"]?.let {
                when (Action.valueOf(it)) {


                    Action.LIKE -> handleLike(

                        Gson().fromJson(
                            remoteMessage.data["content"],
                            Like::class.java
                        )
                    )
                    Action.NEWPOST -> handlerNewPost(
                        Gson().fromJson(
                            remoteMessage.data["content"],
                            NewPost::class.java
                        )
                    )
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    override fun onNewToken(token: String) {
        println(token)
    }

    private fun handlerNewPost(content: NewPost) {
        val notification = NotificationCompat.Builder(this, newPostChannelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                getString(
                    R.string.notification_user_new_post,
                    content.userName
                )
            )
            .setContentText(content.postContent)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(content.postContent)
                    .setSummaryText(content.postContent)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this).notify(Random.nextInt(100_000), notification)
    }


    private fun handleLike(like: Like) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                getString(
                    R.string.notification_user_liked,
                    like.userName,
                    like.postAuthor
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }


}


enum class Action {
    LIKE,
    NEWPOST
}

data class Like(
    val userId: Int,
    val userName: String,
    val postId: Int,
    val postAuthor: String
)

data class NewPost(
    val userId: Long,
    val userName: String,
    val postId: Long,
    val postContent: String
)
