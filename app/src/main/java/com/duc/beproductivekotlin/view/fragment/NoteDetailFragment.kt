package com.duc.beproductivekotlin.view.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.duc.beproductivekotlin.R
import com.duc.beproductivekotlin.databinding.NoteDetailFragmentBinding
import com.duc.beproductivekotlin.db.entities.Note
import com.duc.beproductivekotlin.viewmodel.NoteDetailVM
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailFragment : BaseFragment<NoteDetailFragmentBinding, NoteDetailVM>() {

    override fun getClassViewModel(): Class<NoteDetailVM> = NoteDetailVM::class.java

    override fun initView() {
        val args = NoteDetailFragmentArgs.fromBundle(arguments?: Bundle())
        val note = args.note
        viewModel.color = note.color
        binding.edtNote.setText(note.note)
        initCurrentTime(note)

        val drawable = binding.edtNote.background as GradientDrawable
        drawable.setColor(note.color)
        binding.btSave.setOnClickListener { saveNote(note) }
        binding.ivBack.setOnClickListener { navController.popBackStack() }
        binding.ivColor.setOnClickListener(this)
    }

    private fun saveNote(note: Note) {
        val content = binding.edtNote.text.toString()
        if (content.isNotEmpty()) {
            note.note = content
            note.color = viewModel.color
            viewModel.updateNote(note)
            navController.popBackStack()
        } else {
            showToast("Note is empty!")
        }
    }

    private fun initCurrentTime(note: Note) {
        val currentTime = System.currentTimeMillis()
        val date = Date(currentTime)
        val sdf = SimpleDateFormat("MM dd yyyy, HH:mm", Locale.getDefault())
        val formattedDate = "Thg ${sdf.format(date)}"
        binding.tvDate.text = formattedDate
        note.date = formattedDate
    }

    override fun clickView(view: View) {
        if (view.id == R.id.ivColor) {
            MaterialColorPickerDialog.Builder(requireContext())
                .setColorRes(resources.getIntArray(R.array.note_colors))
                .setTitle("Pick Theme")
                .setColorShape(ColorShape.CIRCLE)
                .setColorSwatch(ColorSwatch._300)
                .setDefaultColor(R.color.note_color1)
                .setColorListener { color, _ ->
                    viewModel.color = color
                    val drawable = binding.edtNote.background as GradientDrawable
                    drawable.setColor(color)
                }
                .show()
        }
    }

    private fun showToast(message: String) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, null)
        val tvMessage = layout.findViewById<TextView>(R.id.tvMessage)
        tvMessage.text = message

        Toast(context).apply {
            duration = Toast.LENGTH_SHORT
            view = layout
            show()
        }
    }

    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        NoteDetailFragmentBinding.inflate(inflater, container, false)
}
