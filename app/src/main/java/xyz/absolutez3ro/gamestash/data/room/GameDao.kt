package xyz.absolutez3ro.gamestash.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GameDao {

    @Query("SELECT * FROM game_tbl ORDER BY rating DESC")
    fun getAllGames(): LiveData<List<Game>>

    @Query("SELECT * FROM game_tbl WHERE name = :name")
    fun getGame(name: String): List<Game>

    @Insert
    fun insertGame(game: Game)

    @Update
    fun updateGame(game: Game)

    @Delete
    fun deleteGame(game: Game)
}