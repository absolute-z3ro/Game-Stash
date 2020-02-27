package xyz.absolutez3ro.gamestash.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import xyz.absolutez3ro.gamestash.R

class MainActivity : AppCompatActivity() {

    private val addActivityRequestCode = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab = findViewById<ExtendedFloatingActionButton>(R.id.extended_fab)
        fab.setOnClickListener {
            startActivityForResult(
                Intent(this, AddActivity::class.java),
                addActivityRequestCode
            )
        }
    }

    private fun setupViews() {

    }
}
