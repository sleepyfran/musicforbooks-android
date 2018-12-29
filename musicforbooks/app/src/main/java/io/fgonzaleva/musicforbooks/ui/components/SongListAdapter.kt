package io.fgonzaleva.musicforbooks.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.fgonzaleva.musicforbooks.R
import io.fgonzaleva.musicforbooks.data.repositories.model.Song
import kotlinx.android.synthetic.main.item_song.view.*

class SongListAdapter : RecyclerView.Adapter<SongListAdapter.ViewHolder>() {

    private val songs: MutableList<Song> = mutableListOf()
    private var unfilteredSongs: List<Song> = listOf()
    lateinit var onSongClick: (Song) -> Unit
    var shouldShowScore = true

    fun updateContent(content: MutableList<Song>) {
        songs.clear()
        songs.addAll(content)
        notifyDataSetChanged()
    }

    fun applyFilter(filter: (song: Song) -> Boolean) {
        unfilteredSongs = songs.toList()
        val filteredList = songs.filter(filter)
        updateContent(filteredList.toMutableList())
    }

    fun <T : Comparable<T>> applySort(selector: (song: Song) -> T) {
        val sortedSongs = songs.sortedBy(selector)
        updateContent(sortedSongs.toMutableList())
    }

    fun unapplyFilter() {
        updateContent(unfilteredSongs.toMutableList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_song, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            val selectedItem = songs[holder.adapterPosition]
            onSongClick(selectedItem)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position], position, shouldShowScore)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(song: Song, index: Int, shouldShowScore: Boolean) {
            view.number.text = (index + 1).toString()

            when (song.score) {
                in 0..49 -> view.score.setImageResource(R.drawable.ic_score_bad)
                in 50..74 -> view.score.setImageResource(R.drawable.ic_score_normal)
                in 75..100 -> view.score.setImageResource(R.drawable.ic_score_good)
            }

            view.songName.text = song.title
            view.artistName.text = song.artist
            view.instrumental.visibility = if (song.isInstrumental) View.VISIBLE else View.INVISIBLE

            if (!shouldShowScore) {
                view.score.visibility = View.GONE
            }
        }
    }

}