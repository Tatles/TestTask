package kakurin.testtask.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kakurin.testtask.R
import kakurin.testtask.data.model.New
import kotlinx.android.synthetic.main.rv_item.view.*


class Adapter(private var callback: (model: New) -> Unit) : RecyclerView.Adapter<Adapter.MyViewHolder>() {
    private var data = listOf<New>()

    fun addData(listItems: List<New>) {
        val sizePast = this.data.size
        this.data = this.data + listItems
        val sizeNew = this.data.size
        notifyItemRangeChanged(sizePast, sizeNew)
    }

    fun setData(data: List<New>) {
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

    class MyViewHolder(itemView: View, private val callback: (model: New) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val imageNews = itemView.image
        private val titleText = itemView.text_title
        private val descriptionText = itemView.text_description
        private val url = itemView.text_url
        fun bind(model: New) {
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