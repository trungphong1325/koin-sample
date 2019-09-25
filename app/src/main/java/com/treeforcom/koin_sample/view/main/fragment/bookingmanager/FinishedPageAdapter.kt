package com.treeforcom.koin_sample.view.main.fragment.bookingmanager

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
import com.treeforcom.koin_sample.model.response.bookingmanager.BookingManagerModel
import kotlinx.android.synthetic.main.item_booking_manager.view.*

class FinishedPageAdapter(private val context: Context) :
    RecyclerView.Adapter<FinishedPageAdapter.ItemViewHolder>() {
    private var mList: List<BookingManagerModel> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_booking_manager,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val model = mList[position]
        holder.numberBooking.text = model.booking_code
        holder.from.text = model.begin_at
        holder.until.text = model.end_at
        Glide.with(context)
            .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.user).optionalCircleCrop())
            .load(model.trainee.avatar)
            .into(holder.avatar)
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: ImageView = view.img_avatar
        val numberBooking: TextView = view.textviewBooking
        val from: TextView = view.textviewFrom
        val until: TextView = view.textviewUntil
    }

    fun setData(list: List<BookingManagerModel>) {
        mList = list
        notifyDataSetChanged()
    }
}