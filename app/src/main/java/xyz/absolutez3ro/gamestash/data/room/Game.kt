package xyz.absolutez3ro.gamestash.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_tbl")
data class Game(
    @PrimaryKey val name: String,
    val description: String,
    val rating: Int
)