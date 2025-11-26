package com.duc.beproductivekotlin.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.duc.beproductivekotlin.R
import com.duc.beproductivekotlin.adapter.NoteAdapter.NoteViewHolder
import com.duc.beproductivekotlin.db.entities.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteAdapter(private var notes: List<Note>, private val context: Context) :
    RecyclerView.Adapter<NoteViewHolder>() {
    private var listener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(note: Note?)
    }

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(v)
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.tvDate.text = note.date
        holder.tvContent.text = note.note
        val bg = holder.lnNote.background.mutate() as GradientDrawable
        bg.setColor(note.color)
        holder.itemView.setOnClickListener {
            if (listener != null) {
                listener!!.onItemClick(note)
            }
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun getNotes(): List<Note> {
        return notes
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvContent: TextView = itemView.findViewById<TextView>(R.id.tvContent)
        var tvDate: TextView = itemView.findViewById<TextView>(R.id.tvDate)
        var lnNote: LinearLayout = itemView.findViewById<LinearLayout>(R.id.lnNote)
    }
}
