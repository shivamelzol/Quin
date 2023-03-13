package com.example.quinstories.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.quinstories.R
import com.example.quinstories.databinding.StoryLtemBinding
import com.example.quinstories.model.Story

class StoryListAdapter: RecyclerView.Adapter<ListViewHolder>() {
    lateinit var context: Context
    var dataList = mutableListOf<Story>()


    @SuppressLint("NotifyDataSetChanged")
    fun setMenuList(list: ArrayList<Story>) {
        this.dataList = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        context = parent.context
        val binding = DataBindingUtil.inflate<StoryLtemBinding>(LayoutInflater.from(parent.context), R.layout.story_ltem, parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val dataModel: Story = dataList[position]
        val list = dataList[position]

        var url = "https://firebasestorage.googleapis.com/v0/b/quinstories-7c423.appspot.com/o/pictures%2F"+dataList[position].image+"?alt=media&token=819710b0-ac68-4dff-b0ff-8ef16fb7deff"

        Glide.with(context)
            .load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .error(R.drawable.car_exterior)
            .into(holder.binding.image)
        holder.bind(list)

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

class ListViewHolder(val binding:StoryLtemBinding ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(list: Story) {
        binding.model=list

        binding.executePendingBindings()
    }
}
