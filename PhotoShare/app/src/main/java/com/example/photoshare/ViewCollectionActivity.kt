package com.example.photoshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_view_collection.*

class ViewCollectionActivity : AppCompatActivity() {

    companion object{
        val collectionItemList: ArrayList<String> = ArrayList()
        val url1 = "https://i.kym-cdn.com/entries/icons/mobile/000/026/008/Screen_Shot_2018-04-25_at_12.24.22_PM.jpg"
        val url2 = "https://i.kym-cdn.com/entries/icons/mobile/000/026/008/Screen_Shot_2018-04-25_at_12.24.22_PM.jpg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_collection)
        collectionItemList.add(url1)
        collectionItemList.add(url2)
        var collectionviewadapter = CollectionViewAdapter(collectionItemList)
        recyclerView.adapter = collectionviewadapter
        recyclerView.layoutManager = LinearLayoutManager(this)//GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
    }
}
