package android.rest.api.themoviedbapi.domain.model

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val id:Int,
    val title:String,
    val overview:String,
    val runtime:Int,
    @SerializedName("release_date")val releaseDate:String,
    @SerializedName("poster_path") val posterPath:String,
    @SerializedName("vote_average")val voteAverage:Double,
    val genres:List<Genre>,


)
