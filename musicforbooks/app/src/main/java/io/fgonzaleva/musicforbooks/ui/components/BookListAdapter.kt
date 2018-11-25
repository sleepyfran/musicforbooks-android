package io.fgonzaleva.musicforbooks.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.fgonzaleva.musicforbooks.R
import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import kotlinx.android.synthetic.main.feed_card_item.view.*

class BookListAdapter() : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    private val bookList: MutableList<BookItem> = mutableListOf()
    lateinit var onBookClick: (BookItem) -> Unit

    fun updateContent(content: MutableList<BookItem>) {
        bookList.clear()
        bookList.addAll(content)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.feed_card_item, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            val selectedItem = bookList[holder.adapterPosition]
            onBookClick(selectedItem)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookList[position])
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: BookItem) {
            view.bookTitle.text = item.bookTitle
            view.bookAuthor.text = item.authorName

            Glide
                .with(view)
                .load(item.coverUrl)
                .into(view.bookCover)
        }
    }

}