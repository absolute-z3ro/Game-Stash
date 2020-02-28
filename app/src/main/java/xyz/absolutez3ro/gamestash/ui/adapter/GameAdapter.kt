package xyz.absolutez3ro.gamestash.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import xyz.absolutez3ro.gamestash.R
import xyz.absolutez3ro.gamestash.data.room.Game

class GameAdapter(private val clickListener: GameClickListener) :
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    var data = listOf<Game>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], clickListener)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gameName: TextView = itemView.findViewById(R.id.name)
        private val gameDesc: TextView = itemView.findViewById(R.id.description)
        private val rating: TextView = itemView.findViewById(R.id.rating)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.delete)

        fun bind(game: Game, clickListener: GameClickListener) {
            gameName.text = game.name
            gameDesc.text = game.description
            rating.text = game.rating.toString()
            deleteButton.setOnClickListener {
                clickListener.onClick(game)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.game_item, parent, false)
                return ViewHolder(view)
            }
        }
    }
}

class GameDiffCallback : DiffUtil.ItemCallback<Game>() {
    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.name == newItem.name
    }
}

class GameClickListener(val clickListener: (game: Game) -> Unit) {
    fun onClick(game: Game) = clickListener(game)
}