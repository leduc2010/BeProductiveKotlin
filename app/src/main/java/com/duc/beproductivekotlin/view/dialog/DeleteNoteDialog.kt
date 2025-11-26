package com.duc.beproductivekotlin.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.duc.beproductivekotlin.R

class DeleteNoteDialog(context: Context, private val listener: OnDeleteListener) : Dialog(context) {
    interface OnDeleteListener {
        fun onConfirmDelete()

        fun onCancelDelete()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_note_dialog)

        if (window != null) {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        val tvCancel = findViewById<TextView>(R.id.tvCancel)
        val tvDelete = findViewById<TextView>(R.id.tvDelete)

        tvCancel.setOnClickListener {
            listener.onCancelDelete()
            dismiss()
        }
        tvDelete.setOnClickListener {
            listener.onConfirmDelete()
            dismiss()
        }
    }
}
