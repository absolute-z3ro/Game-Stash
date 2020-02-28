package xyz.absolutez3ro.gamestash.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.absolutez3ro.gamestash.data.room.Game
import xyz.absolutez3ro.gamestash.data.viewmodel.GameViewModel
import xyz.absolutez3ro.gamestash.databinding.ActivityMainBinding
import xyz.absolutez3ro.gamestash.ui.adapter.GameAdapter
import xyz.absolutez3ro.gamestash.ui.adapter.GameClickListener

class MainActivity : AppCompatActivity() {

    private val addActivityRequestCode = 7

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: GameViewModel
    private lateinit var adapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerView
        val nothingToDisplay = binding.nothingToDisplay
        val fab = binding.extendedFab
        val appBarLayout = binding.appBarLayout

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        viewModel.games?.observe(this, Observer { games ->
            games.let {
                if (it.isNotEmpty()) nothingToDisplay.visibility = View.GONE
                else nothingToDisplay.visibility = View.VISIBLE
                adapter.data = it
            }
        })

        adapter = GameAdapter(GameClickListener {
            viewModel.delete(it)
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView.canScrollVertically(-1))
                    appBarLayout.elevation = 8.0f
                else
                    appBarLayout.elevation = 0.0f
            }
        }
        recyclerView.addOnScrollListener(scrollListener)

        fab.setOnClickListener {
            startActivityForResult(
                Intent(this, AddActivity::class.java),
                addActivityRequestCode
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == addActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getBundleExtra(AddActivity.EXTRA_REPLY).let {
                val game = Game(
                    it!!.getString(AddActivity.NAME)!!,
                    it.getString(AddActivity.DESC)!!,
                    it.getInt(AddActivity.RATING, 0)
                )
                viewModel.insert(game)
            }
        }
    }
}
