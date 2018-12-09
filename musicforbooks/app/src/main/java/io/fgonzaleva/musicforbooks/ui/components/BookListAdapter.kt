package io.fgonzaleva.musicforbooks.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.fgonzaleva.musicforbooks.R
import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import kotlinx.android.synthetic.main.item_book_card.view.*

class BookListAdapter : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    private val books: MutableList<BookItem> = mutableListOf()
    lateinit var onBookClick: (BookItem) -> Unit

    fun updateContent(content: MutableList<BookItem>) {
        books.clear()
        books.addAll(content)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_book_card, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            val selectedItem = books[holder.adapterPosition]
            onBookClick(selectedItem)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(books[position])
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