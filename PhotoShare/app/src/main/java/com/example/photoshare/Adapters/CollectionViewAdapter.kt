package com.example.photoshare.Adapters

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.photoshare.Activities.ViewCollectionActivity
import com.example.photoshare.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class CollectionViewViewHolder(inflater: LayoutInflater,
                               parent: ViewGroup,
                               private val storage: FirebaseStorage,
                               private val vca: ViewCollectionActivity) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.collection_view_item, parent, false)) {
    private val image: ImageView = itemView.findViewById(R.id.collectionItem)
    private val deleteBtn: Button = itemView.findViewById(R.id.deleteCollectionPictureButton)


    fun bind(url: Uri, path: String, i: Int) {
        Picasso.get().load(url).fit().centerCrop().into(image)
        deleteBtn.setOnClickListener {
            storage.getReference(path).delete().addOnCompleteListener {
                vca.loadCollectionImages()
            }
        }
    }

}

class CollectionViewAdapter(private val list: ArrayList<Uri>,
                            private val pathList: ArrayList<String>,
                            private val storage: FirebaseStorage,
                            private val vca: ViewCollectionActivity)
    : RecyclerView.Adapter<CollectionViewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CollectionViewViewHolder(inflater, parent, storage, vca)
    }

    override fun onBindViewHolder(holder: CollectionViewViewHolder, position: Int) {
        val url = list[position]
        holder.bind(url, pathList[position], position)
    }

    override fun getItemCount(): Int = list.size

}

