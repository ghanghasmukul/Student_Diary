package com.example.studentdiary.adapters

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.studentdiary.R
import com.example.studentdiary.databinding.RvItemBinding
import com.example.studentdiary.roomdatabase.StudentDetails
import com.example.studentdiary.roomdatabase.StudentViewModel
import kotlinx.android.synthetic.main.update_records_dialog.*


class ViewRecordsAdapter(var context: Context, private var studentList: ArrayList<StudentDetails>) :
    RecyclerView.Adapter<ViewRecordsAdapter.ViewRecordViewHolder>() {
    var studentViewModel = StudentViewModel()

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
                addressLine.text = studentList[position].address_line
                locality.text = studentList[position].locality

                textViewOptions.setOnClickListener {
                    val popup = PopupMenu(context, textViewOptions)
                    popup.inflate(R.menu.options_menu)

                    popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                        override fun onMenuItemClick(item: MenuItem?): Boolean {
                            when (item?.itemId) {
                                R.id.menuItemDelete -> {
                                    Toast.makeText(
                                        context,
                                        "menu delete clicked",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    studentViewModel.delete(
                                        studentList[holder.absoluteAdapterPosition]
                                    )
                                    notifyItemRemoved(holder.absoluteAdapterPosition)
                                    return true
                                }
                                R.id.menuItemEdit -> {
                                    val pos : StudentDetails = studentList[holder.absoluteAdapterPosition]
                                    val name : String = pos.name.toString()
                                    val phone : String = pos.phone_no.toString()
                                    val roll : String = pos.roll_no.toString()
                                    val loc : String = pos.locality.toString()
                                    val addLine : String = pos.address_line.toString()
                                    val dialog = Dialog(context)
                                    dialog.setContentView(R.layout.update_records_dialog)
                                    val width : Int = WindowManager.LayoutParams.MATCH_PARENT
                                    val height : Int = WindowManager.LayoutParams.WRAP_CONTENT
                                    dialog.window?.setLayout(width,height)
                                    dialog.show()
                                    val nameET : EditText = dialog.etUpdateName
                                    val phoneET :EditText = dialog.etUpdatePhoneNo
                                    val rollET : EditText = dialog.etUpdateRollNo
                                    val addressLine : EditText = dialog.etUpdateAddressLine
                                    val locality : EditText = dialog.etUpdateLocality
                                    val updateBtn : Button = dialog.updateBtn

                                    nameET.setText(name)
                                    phoneET.setText(phone)
                                    rollET.setText(roll)
                                    addressLine.setText(addLine)
                                    locality.setText(loc)
                                    updateBtn.setOnClickListener{
                                        dialog.dismiss()
                                        val updatedName = nameET.text.toString().trim()
                                        val updatedRoll = rollET.text.toString().trim()
                                        val updatedPhone = phoneET.text.toString().trim()
                                        val updatedLoc = locality.text.toString().trim()
                                        val updatedAddressLine = addressLine.text.toString().trim()
                                        val studentDetails1 = StudentDetails(updatedName,updatedRoll,updatedPhone,updatedAddressLine,updatedLoc)
                                     //   studentViewModel.updateStudent(studentDetails1)
//                                        pos.id?.let { it1 ->
//                                            studentViewModel.update(
//                                                it1,updatedName)
//
//                                        }

                                        pos.id?.let { it1 ->
                                            studentViewModel.updateStudentDetails(
                                                it1,studentDetails1)
                                        }
                                     //   studentViewModel.updateStudentDetails( StudentDetails(updatedName,updatedRoll,updatedPhone,updatedAddressLine,updatedLoc))
                                      //  studentViewModel.updateStudent1( StudentDetails(updatedName,updatedRoll,updatedPhone,updatedAddressLine,updatedLoc))
                                        studentViewModel.getAllUserData(context)

                                    }
                                    return true
                                }
                            }
                            return false
                        }
                    })
                    popup.show()
                }

            } else {
                Toast.makeText(context, "no Data found", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun setData(studentList: ArrayList<StudentDetails>) {
        this.studentList = studentList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

}
