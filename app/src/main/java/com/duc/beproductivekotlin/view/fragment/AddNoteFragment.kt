package com.duc.beproductivekotlin.view.fragment

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.duc.beproductivekotlin.R
import com.duc.beproductivekotlin.databinding.AddNoteFragmentBinding
import com.duc.beproductivekotlin.viewmodel.AddNoteViewModel
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import java.text.SimpleDateFormat
import java.util.*

class AddNoteFragment : BaseFragment<AddNoteFragmentBinding, AddNoteViewModel>() {

    override fun getClassViewModel(): Class<AddNoteViewModel> = AddNoteViewModel::class.java

    override fun initView() {
        initCurrentTime()
        initRandomColor()
        binding.ivBack.setOnClickListener { navController.popBackStack() }
    }

    private fun initRandomColor() {
        val color = getRandomColor(requireContext())
        val drawable = binding.edtNote.background as GradientDrawable
        drawable.setColor(color)
        viewModel.setColor(color)
    }

    private fun initCurrentTime() {
        val currentTime = System.currentTimeMillis()
        val date = Date(currentTime)
        val sdf = SimpleDateFormat("MM dd yyyy, HH:mm", Locale.getDefault())
        val formattedDate = "Thg ${sdf.format(date)}"
        binding.tvDate.text = formattedDate

        binding.ivColor.setOnClickListener(this)
        binding.btSave.setOnClickListener(this)
    }

    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        AddNoteFragmentBinding.inflate(inflater, container, false)

    override fun clickView(view: View) {
        when (view.id) {
            R.id.ivColor -> {
                MaterialColorPickerDialog.Builder(requireContext())
                    .setColorRes(resources.getIntArray(R.array.note_colors))
                    .setTitle("Pick Theme")
                    .setColorShape(ColorShape.CIRCLE)
                    .setColorSwatch(ColorSwatch._300)
                    .setDefaultColor(R.color.note_color1)
                    .setColorListener { color, _ ->
                        viewModel.setColor(color)
                        val drawable = binding.edtNote.background as GradientDrawable
                        drawable.setColor(color)
                    }
                    .show()
            }

            R.id.btSave -> {
                val noteText = binding.edtNote.text
                if (noteText.isNullOrEmpty()) {
                    showToast("Note is empty!")
                } else {
                    viewModel.saveNote(noteText.toString())
                    showToast("Note is saved!")
                    navController.popBackStack()
                }
            }
        }
    }

    private fun getRandomColor(context: Context): Int {
        val colors = context.resources.getIntArray(R.array.note_colors)
        return colors.random()
    }

    private fun showToast(message: String) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, null)
        val tvMessage: TextView = layout.findViewById(R.id.tvMessage)
        tvMessage.text = message

        Toast(context).apply {
            duration = Toast.LENGTH_SHORT
            view = layout
        }.show()
    }
}
