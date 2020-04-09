package com.example.photoshare


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {

    companion object {
        var imagePost1: ImagePost = ImagePost("https://i.kym-cdn.com/entries/icons/mobile/000/026/008/Screen_Shot_2018-04-25_at_12.24.22_PM.jpg", "email.email", "YOOOOOOOOO")
        var imagePost2: ImagePost = ImagePost("https://i.kym-cdn.com/entries/icons/mobile/000/026/008/Screen_Shot_2018-04-25_at_12.24.22_PM.jpg", "SOOOO GOOOD", "SHOO BOUY")
        var imageList: ArrayList<ImagePost> = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageList.add(imagePost1)
        imageList.add(imagePost2)
        var imageadapter = ImageAdapter(imageList)
        recyclerView.adapter = imageadapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

}
