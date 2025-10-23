package android.rest.api.themoviedbapi.data.local.dao

import android.content.Context
import android.rest.api.themoviedbapi.domain.model.Movie
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false

)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}