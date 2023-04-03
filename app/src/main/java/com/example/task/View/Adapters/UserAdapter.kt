package com.example.task.View.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task.Model.UserList
import com.example.task.databinding.CustomUserLayoutBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.MyView> {
    private lateinit var binding: CustomUserLayoutBinding
    private lateinit var mContext: Context
    private lateinit var data: ArrayList<UserList>
    private lateinit var click: OnUserListener

    constructor(mContext: Context, data: ArrayList<UserList>, click: OnUserListener) {
        this.mContext = mContext
        this.data = data
        this.click = click
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val inflater = LayoutInflater.from(parent.context)

        binding = CustomUserLayoutBinding.inflate(inflater, parent, false)

        return MyView(binding.root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        binding.txtFirst.text = data[position].first_name
        binding.txtLast.text = data[position].last_name
        binding.txtGender.text = data[position].gender
        binding.txtProfession.text = data[position].profession

        holder.itemView.setOnClickListener {
            click.onUserDetailsClickedListener(position, data)
        }
        holder.itemView.setOnLongClickListener {
            click.onUserDeleteClickedListener(position, data)

            true
        }

    }

    inner class MyView(itemView: View) : RecyclerView.ViewHolder(itemView) {}


    interface OnUserListener {
        fun onUserDetailsClickedListener(position: Int, data: ArrayList<UserList>)
        fun onUserDeleteClickedListener(position: Int, data: ArrayList<UserList>)
    }


}