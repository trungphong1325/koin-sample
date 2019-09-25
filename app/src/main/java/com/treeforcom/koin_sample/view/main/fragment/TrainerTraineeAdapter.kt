package com.treeforcom.koin_sample.view.main.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.treeforcom.koin_sample.R
import com.treeforcom.koin_sample.model.response.listuser.TrainerTraineeDetailModel
import kotlinx.android.synthetic.main.item_trainner_trainee.view.*

class TrainerTraineeAdapter(private val context: Context) :
    RecyclerView.Adapter<TrainerTraineeAdapter.ItemViewHolder>() {
    private var mListTrainerTrainee: MutableList<TrainerTraineeDetailModel> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_trainner_trainee,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mListTrainerTrainee.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val model = mListTrainerTrainee[position]
        holder.name.text = model.name
        holder.distance.text = model.distance.toString()
        holder.hour.text = model.hour.toString()
        holder.rating.text = model.rating.toString()
        Glide.with(context).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.user).optionalCircleCrop()).load(model.avatar)
            .into(holder.avatar)
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.tv_name
        val distance: TextView = view.tv_distance
        val hour: TextView = view.tv_hour
        val rating: TextView = view.tv_rating
        val avatar: ImageView = view.img_avatar
    }

    fun setData(list: List<TrainerTraineeDetailModel>) {
        mListTrainerTrainee.clear()
        mListTrainerTrainee = list.toMutableList()
        notifyDataSetChanged()
    }
    fun addData(list: List<TrainerTraineeDetailModel>){
        mListTrainerTrainee.addAll(list)
        notifyDataSetChanged()
    }
}