package com.fakhrulasa.realmdb.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.fakhrulasa.realmdb.R
import com.fakhrulasa.realmdb.ui.models.CatItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.Main)
    private var cat: ImageView? = null
    private var item: CatItem? = null
    private val repository by lazy { (applicationContext as App).repository }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        cat = findViewById<ImageView>(R.id.img_cat)
        val dislike = findViewById<ImageView>(R.id.dislike)
        val favourites = findViewById<ImageView>(R.id.favourites)
        val like = findViewById<ImageView>(R.id.like)
        dislike.setOnClickListener { loadNewImage() }
        like.setOnClickListener { saveLike(item) }
        loadNewImage()
        favourites.setOnClickListener {
            val intent = Intent(this, FavouriteCatsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadNewImage() {
        scope.launch {
            item = null
            cat?.setImageResource(R.drawable.loading)
            item = repository.getNewImage()
            if (item != null) {
                val uri = Uri.parse(item?.url)
                cat?.setImageURI(uri)
            }
        }
    }

    private fun saveLike(item: CatItem?) {
        scope.launch {
            if (item != null) {
                loadNewImage()
                repository.saveFavCats(item)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
