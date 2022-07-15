package com.sirius.test_app.itemOfRecyclerView

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sirius.test_app.R
import com.sirius.test_app.ReviewModel
import java.lang.Exception
import java.lang.IllegalArgumentException

class RecyclerViewAdapter(private val data: List<Any>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_MAIN_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.main_header_layout, parent, false)
                MainHeaderViewHolder(view, context)
            }
            TYPE_DESCRIPTION -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.description_layout, parent, false)
                DescriptionViewHolder(view)
            }
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_layout, parent, false)
                HeaderViewHolder(view)
            }
            TYPE_REVIEW -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.review_layout, parent, false)
                ReviewViewHolder(view, context)
            }
            TYPE_RATING -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.rating_layout, parent, false)
                RatingViewHolder(view, context)
            }
            TYPE_GENRE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.genre_layout, parent, false)
                GenreViewHolder(view, context)
            }
            TYPE_INSTALL -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.button_install_layout, parent, false)
                InstallViewHolder(view, context)
            }
            TYPE_VIDEOS -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.video_layout, parent, false)
                VideosViewHolder(view, context)
            }
            else -> throw IllegalArgumentException("Invalid: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val element = data[position]
        when(holder) {
            is MainHeaderViewHolder -> holder.bind(element as MainHeader)
            is DescriptionViewHolder -> holder.bind(element as Description)
            is HeaderViewHolder -> holder.bind(element as Header)
            is ReviewViewHolder -> holder.bind(element as ReviewModel)
            is RatingViewHolder -> holder.bind(element as Rating)
            is GenreViewHolder -> holder.bind(element as Genre)
            is InstallViewHolder -> holder.bind(element as Install)
            is VideosViewHolder -> holder.bind(element as Videos)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return when(data[position]) {
            is MainHeader -> TYPE_MAIN_HEADER
            is Description -> TYPE_DESCRIPTION
            is Header -> TYPE_HEADER
            is ReviewModel -> TYPE_REVIEW
            is Rating -> TYPE_RATING
            is Genre -> TYPE_GENRE
            is Install -> TYPE_INSTALL
            is Videos -> TYPE_VIDEOS
            else -> throw IllegalArgumentException("Invalid view class: ${data[position].javaClass}")
        }
    }

    class DescriptionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val description = view.findViewById<TextView>(R.id.description)
        fun bind(item: Description) {
            description.text = item.description
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val header = view.findViewById<TextView>(R.id.header)
        fun bind(item: Header) {
            header.text = item.header
        }
    }

    class ReviewViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        private val nameOfUser = view.findViewById<TextView>(R.id.nameOfUser)
        private val dateOfReview = view.findViewById<TextView>(R.id.dateOfReview)
        private val messageOfUser = view.findViewById<TextView>(R.id.messageOfUser)
        private val imageOfUser = view.findViewById<ImageView>(R.id.imageOfUser)
        fun bind(item: ReviewModel) {
            Glide.with(context)
                .load(item.userImage)
                .transform(CircleCrop())
                .into(imageOfUser)
            nameOfUser.text = item.userName
            dateOfReview.text = item.date
            messageOfUser.text = item.message
        }
    }

    class RatingViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        private val numberOfRating = view.findViewById<TextView>(R.id.numberOfRating)
        private val infoOfPopularity = view.findViewById<TextView>(R.id.infoOfPopularity)
        private val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        fun bind(item: Rating) {
            numberOfRating.text = item.rating.toString()
            infoOfPopularity.text = context.resources.getString(R.string.Reviews, item.fame)
            ratingBar.rating = item.rating

        }
    }

    class MainHeaderViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        private val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        private val mainHeader = view.findViewById<TextView>(R.id.mainHeader)
        private val infoOfPopularity = view.findViewById<TextView>(R.id.infoOfPopularity)
        private val logo = view.findViewById<ImageView>(R.id.logo)
        fun bind(item: MainHeader) {
            Glide.with(context)
                .load(R.drawable.img_logo)
                .transform(CenterCrop())
                .into(logo)
            mainHeader.text = item.mainHeader
            ratingBar.rating = item.rating
            infoOfPopularity.text = item.infoOfFame
        }
    }

    class GenreViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        private val btnMOBA = view.findViewById<Button>(R.id.btnMOBA)
        private val btnMultiplayer = view.findViewById<Button>(R.id.btnMultiplayer)
        private val btnStrategy = view.findViewById<Button>(R.id.btnStrategy)
        fun bind(item: Genre) {
            try {
                btnMOBA.text = item.listGenre[0]
                btnMultiplayer.text = item.listGenre[1]
                btnStrategy.text = item.listGenre[2]
                btnMOBA.setOnClickListener {
                    Toast.makeText(context, "MOBA", Toast.LENGTH_SHORT).show()
                }
                btnMultiplayer.setOnClickListener {
                    Toast.makeText(context, "Multiplayer", Toast.LENGTH_SHORT).show()
                }
                btnStrategy.setOnClickListener {
                    Toast.makeText(context, "Strategy", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("Error", e.toString())
            }
        }
    }

    class InstallViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        private val btnInstall = view.findViewById<Button>(R.id.btnInstall)
        fun bind(item: Install) {
            btnInstall.text = item.textButtonInstall
            btnInstall.setOnClickListener {
                Toast.makeText(context, item.action, Toast.LENGTH_SHORT).show()
            }
        }
    }

    class VideosViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        private val videoOfGame1 = view.findViewById<ImageView>(R.id.videoOfGame1)
        private val videoOfGame2 = view.findViewById<ImageView>(R.id.videoOfGame2)
        fun bind(item: Videos) {
            if (item.videos[0].id == "video_1") {
                Glide.with(context)
                    .load(item.videos[0].image)
                    .into(videoOfGame1)
            }
            if (item.videos[1].id == "video_2") {
                Glide.with(context)
                    .load(item.videos[1].image)
                    .into(videoOfGame2)
            }
        }
    }

    companion object {
        private const val TYPE_MAIN_HEADER = 0
        private const val TYPE_DESCRIPTION = 1
        private const val TYPE_HEADER = 2
        private const val TYPE_REVIEW = 3
        private const val TYPE_RATING = 4
        private const val TYPE_GENRE = 5
        private const val TYPE_INSTALL = 6
        private const val TYPE_VIDEOS = 7
    }
}