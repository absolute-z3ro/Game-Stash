package xyz.absolutez3ro.gamestash.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {

    @Query("SELECT * FROM game_tbl ORDER BY rating DESC")
    fun getAllGames(): LiveData<List<Game>>

    @Insert
    fun insertGame(game: Game)

    @Delete
    fun deleteGame(game: Game)
}