package com.example.studentdiary.adapters

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.*
import android.widget.*
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.studentdiary.R
import com.example.studentdiary.databinding.RvItemBinding
import com.example.studentdiary.roomdatabase.StudentDetails
import com.example.studentdiary.roomdatabase.StudentViewModel
import com.example.studentdiary.typeConverters.Converters
import kotlinx.android.synthetic.main.fragment_view_records.view.*
import kotlinx.android.synthetic.main.update_records_dialog.*


class ViewRecordsAdapter(var context: Context, private var studentList: ArrayList<StudentDetails>) :
    RecyclerView.Adapter<ViewRecordsAdapter.ViewRecordViewHolder>() {
    var studentViewModel = StudentViewModel()
    lateinit var byteArray: ByteArray
    var checkedPosition = 0

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
                val bitmapImage = Converters().toBitmap(studentList[position].profile_photo)

                profilePic.setImageBitmap(bitmapImage)
                profilePic.setOnClickListener {
                    if (selectRV.isGone) {
                        selectRV.visibility = View.VISIBLE
                        notifyDataSetChanged()
                    }
                    else
                        if (selectRV.isInvisible) {
                        selectRV.visibility = View.VISIBLE
                        notifyDataSetChanged()
                    }
                        else {
                        selectRV.visibility = View.GONE
                        notifyDataSetChanged()
                    }
                    Toast.makeText(context, "OnLongPressed", Toast.LENGTH_SHORT).show()
                }
                byteArray = studentList[position].profile_photo
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
                                    val pos: StudentDetails =
                                        studentList[holder.absoluteAdapterPosition]
                                    val name: String = pos.name.toString()
                                    val phone: String = pos.phone_no.toString()
                                    val roll: String = pos.roll_no.toString()
                                    val addLine: String = pos.address_line.toString()
                                    val dialog = Dialog(context)
                                    dialog.setContentView(R.layout.update_records_dialog)
                                    val width: Int = WindowManager.LayoutParams.MATCH_PARENT
                                    val height: Int = WindowManager.LayoutParams.WRAP_CONTENT
                                    dialog.window?.setLayout(width, height)
                                    dialog.show()
                                    val nameET: EditText = dialog.etUpdateName
                                    val phoneET: EditText = dialog.etUpdatePhoneNo
                                    val rollET: EditText = dialog.etUpdateRollNo
                                    val addressLine: EditText = dialog.etUpdateAddressLine
                                    val updateBtn: Button = dialog.updateBtn

                                    nameET.setText(name)
                                    phoneET.setText(phone)
                                    rollET.setText(roll)
                                    addressLine.setText(addLine)
                                    val byteArray = pos.profile_photo
                                    updateBtn.setOnClickListener {
                                        val updatedName = nameET.text.toString().trim()
                                        val updatedRoll = rollET.text.toString().trim()
                                        val updatedPhone = phoneET.text.toString().trim()
                                        val updatedAddressLine = addressLine.text.toString().trim()
                                        val studentDetails1 =
                                            StudentDetails(
                                                updatedName,
                                                updatedRoll,
                                                updatedPhone,
                                                updatedAddressLine,
                                                byteArray
                                            )

                                        if (updatedName.isEmpty() || updatedPhone.isEmpty() || updatedRoll.isEmpty() || updatedAddressLine.isEmpty() || updatedPhone.length != 10 || updatedRoll.length != 4) {
                                            Toast.makeText(
                                                context,
                                                "Fill carefully Something Wrong",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            if (updatedName.isEmpty() || updatedName.length < 3) {
                                                Toast.makeText(
                                                    context,
                                                    "Fill name carefully",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            if (updatedPhone.isEmpty() || updatedPhone.length != 10) {
                                                Toast.makeText(
                                                    context,
                                                    "Enter valid Phone no",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                            }
                                            if (updatedRoll.isEmpty() || updatedRoll.length != 4) {
                                                Toast.makeText(
                                                    context,
                                                    "Enter valid Roll no.",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                            }
                                            if (updatedAddressLine.isEmpty() || updatedAddressLine.length < 3) {
                                                Toast.makeText(
                                                    context,
                                                    "Enter valid Address",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } else {

                                            pos.id?.let { it1 ->
                                                if (studentDetails1 != null) {
                                                    studentViewModel.updateStudentDetails(
                                                        it1, studentDetails1
                                                    )
                                                }
                                            }

                                            studentViewModel.getAllUserData(context)
                                            dialog.dismiss()

                                        }
                                    }
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


    fun setData(studentList: List<StudentDetails>) {
        this.studentList = studentList as ArrayList<StudentDetails>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return studentList.size
    }


}
