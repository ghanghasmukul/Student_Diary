package com.example.studentdiary.Adapters

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.studentdiary.databinding.RvItemBinding
import com.example.studentdiary.roomdatabase.StudentDetails
import com.example.studentdiary.roomdatabase.StudentViewModel
import com.example.studentdiary.ui.add_records.AddRecordsFragment


class ViewRecordsAdapter() :
    RecyclerView.Adapter<ViewRecordsAdapter.ViewRecordViewHolder>() {
    private var studentList: ArrayList<StudentDetails> = arrayListOf()

    private lateinit var dataList : StudentViewModel
    lateinit var context : Context
    inner class ViewRecordViewHolder(val binding: RvItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewRecordViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewRecordViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewRecordsAdapter.ViewRecordViewHolder, position: Int) {

        holder.binding.apply {
                tvName.text = studentList[position].name
                tvPhone.text = studentList[position].phone_no
                tvRoll.text = studentList[position].roll_no
            dataList.getAllUserData(context)
            AddRecordsFragment().setData(studentList)
            AddRecordsFragment().setData(studentList)
            notifyDataSetChanged()
            textViewOptions.setOnClickListener {
                var context: Context? = null
                val popup = PopupMenu(context, textViewOptions)
                popup.inflate(com.example.studentdiary.R.menu.options_menu)

                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        com.example.studentdiary.R.id.menuItemEdit ->

                            true

                        com.example.studentdiary.R.id.menuItemDelete ->
                            true

                        else -> false
                    }

                }
                popup.show()
            }

        }
    }


    override fun getItemCount(): Int {
          return studentList.size

    }

}
