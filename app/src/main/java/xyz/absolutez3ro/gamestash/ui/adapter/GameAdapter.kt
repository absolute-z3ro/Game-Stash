package xyz.absolutez3ro.gamestash.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import xyz.absolutez3ro.gamestash.data.room.Game
import xyz.absolutez3ro.gamestash.databinding.GameItemBinding

class GameAdapter(private val clickListener: GameClickListener) :
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    var data = listOf<Game>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = GameItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder.from(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], clickListener)
    }


    class ViewHolder private constructor(private val binding: GameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(game: Game, clickListener: GameClickListener) {
            binding.name.text = game.name
            binding.description.text = game.description
            binding.rating.text = game.rating.toString()
            binding.delete.setOnClickListener {
                clickListener.onClick(game)
            }
        }

        companion object {
            fun from(binding: GameItemBinding): ViewHolder {
                return ViewHolder(binding)
            }
        }
    }
}

class GameClickListener(val clickListener: (game: Game) -> Unit) {
    fun onClick(game: Game) = clickListener(game)
}