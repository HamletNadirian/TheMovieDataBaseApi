package android.rest.api.themoviedbapi.di

import android.content.Context
import android.rest.api.themoviedbapi.data.local.dao.AppDatabase
import android.rest.api.themoviedbapi.data.local.dao.FavoriteMovieDao
import android.rest.api.themoviedbapi.domain.repository.MovieRepository
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "favorite_movies"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieDao(database: AppDatabase): FavoriteMovieDao {
        return database.favoriteMovieDao()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(dao: FavoriteMovieDao): MovieRepository {
        return MovieRepository(dao)
    }
}