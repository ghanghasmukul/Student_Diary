package com.example.studentdiary.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.studentdiary.R
import com.example.studentdiary.databinding.RvItemBinding
import com.example.studentdiary.roomdatabase.StudentDetails
import com.example.studentdiary.roomdatabase.StudentViewModel
import com.example.studentdiary.ui.add_records.AddRecordsFragment


class ViewRecordsAdapter(var context: Context,private var studentList:ArrayList<StudentDetails>) :
    RecyclerView.Adapter<ViewRecordsAdapter.ViewRecordViewHolder>() {

     var studentViewModel : StudentViewModel = StudentViewModel()
    inner class ViewRecordViewHolder(val binding: RvItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewRecordViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewRecordViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewRecordViewHolder, position: Int) {

        holder.binding.apply {
            if (studentList.size != 0) {
                tvName.text = studentList[position].name?.uppercase()
                tvPhone.text = studentList[position].phone_no
                tvRoll.text = studentList[position].roll_no

                textViewOptions.setOnClickListener {
                    val popup = PopupMenu(context, textViewOptions)
                    popup.inflate(R.menu.options_menu)

                    popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                        override fun onMenuItemClick(item: MenuItem?): Boolean {
                            when(item?.itemId) {
                                R.id.menuItemDelete -> {
                                    Toast.makeText(context, "menu delete clicked", Toast.LENGTH_SHORT)
                                        .show()
                                    studentViewModel.delete(context,studentList[holder.absoluteAdapterPosition])
                                    notifyItemRemoved(holder.absoluteAdapterPosition)

                                    return true
                                }
                                // in the same way you can implement others
                              R.id.menuItemEdit -> {



                                    // define
                                    Toast.makeText(context, "menu edit clicked", Toast.LENGTH_SHORT)
                                        .show()
                                    return true
                                }
                            }
                            return false
                        }
                    })
                    popup.show()
                }

            }else{
                Toast.makeText(context,"no Data found",Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun setData(studentList:ArrayList<StudentDetails>)
    {
        this.studentList=studentList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

}
