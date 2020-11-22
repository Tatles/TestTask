package kakurin.testtask.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kakurin.testtask.R
import kakurin.testtask.data.model.Article
import kotlinx.android.synthetic.main.rv_item.view.*


class NewsAdapter(private val callback: (Article) -> Unit) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    private var data = listOf<Article>()

    fun addData(listItems: List<Article>) {
        val sizePast = this.data.size
        this.data = this.data + listItems
        val sizeNew = this.data.size
        notifyItemRangeChanged(sizePast, sizeNew)
    }

    fun setData(data: List<Article>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false), callback
        )


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class MyViewHolder(itemView: View, private val callback: (Article) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val imageNews = itemView.image
        private val titleText = itemView.text_title
        private val descriptionText = itemView.text_description
        private val url = itemView.text_url
        fun bind(model: Article) {
            titleText.text = model.title
            descriptionText.text = model.description
            url.text = model.url
            Picasso.get().load(model.urlToImage).centerCrop().fit().into(imageNews)
            url.setOnClickListener{
                callback.invoke(model)
            }
        }
    }
}