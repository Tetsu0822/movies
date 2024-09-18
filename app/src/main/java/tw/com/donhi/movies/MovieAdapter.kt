package tw.com.donhi.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import tw.com.donhi.movies.databinding.RowMovieBinding

class MovieAdapter(val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val view: RowMovieBinding) : ViewHolder(view.root) {

        init {
            val posterImage = view.moviePoster
            val titleText = view.movieTitle
            val popText = view.moviePop
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = RowMovieBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies.get(position)
        holder.view.movieTitle.text = movie.title
        holder.view.moviePop.text = movie.popularity.toString()
        holder.view.moviePoster.load("https://image.tmdb.org/t/p/w500${movie.poster_path}") {

        }
    }
}