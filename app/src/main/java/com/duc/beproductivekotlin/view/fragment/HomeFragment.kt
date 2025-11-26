package com.duc.beproductivekotlin.view.fragment

import android.graphics.Canvas
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.duc.beproductivekotlin.R
import com.duc.beproductivekotlin.adapter.NoteAdapter
import com.duc.beproductivekotlin.databinding.HomeFragmentBinding
import com.duc.beproductivekotlin.db.entities.Note
import com.duc.beproductivekotlin.view.dialog.DeleteNoteDialog
import com.duc.beproductivekotlin.view.dialog.DeleteNoteDialog.OnDeleteListener
import com.duc.beproductivekotlin.viewmodel.HomeVM
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.Locale
import java.util.Objects

class HomeFragment : BaseFragment<HomeFragmentBinding, HomeVM>() {
    private var adapter: NoteAdapter? = null

    override fun getClassViewModel(): Class<HomeVM> {
        return HomeVM::class.java
    }

    override fun initView() {
        adapter = NoteAdapter(mutableListOf(), requireContext())
        adapter?.setOnItemClickListener { note: Note? ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToNoteDetailFragment(note!!) as NavDirections
            navController.navigate(action)
        }
        binding.rvNote.setAdapter(adapter)
        binding.ivAdd.setOnClickListener(this)
        onSearch()
        viewModel.listNotes.observe(viewLifecycleOwner) { notes ->
            adapter?.setNotes(notes.toMutableList())
        }
        deleteNote()
    }

    override fun onResume() {
        binding.searchView.isIconified = true
        super.onResume()
    }

    private fun onSearch() {
        binding.searchView.setOnSearchClickListener { v: View? ->
            binding.tvAllNotes.visibility = View.GONE
            val closeButton = binding.searchView.findViewById<ImageView>(
                androidx.appcompat.R
                    .id.search_close_btn
            )
            closeButton.setColorFilter(ContextCompat.getColor(requireContext(), com.github.dhaval2404.colorpicker.R.color.black))

            val searchPlate =
                binding.searchView.findViewById<View>(androidx.appcompat.R.id.search_plate)
            searchPlate.setBackground(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.custom_search
                )
            )

            val searchEditText =
                binding.searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            searchEditText.setTextColor(Color.BLACK)
            searchEditText.setHintTextColor(Color.BLACK)
            searchEditText.setTextSize(14f)
            searchEditText.setCursorVisible(true)
            searchEditText.requestFocus()
            search()
        }

        binding.searchView.setOnCloseListener {
            binding.tvAllNotes.visibility = View.VISIBLE
            adapter?.setNotes(viewModel.listNotes.value ?: mutableListOf())
            false
        }
    }

    private fun deleteNote() {
        val itemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.getBindingAdapterPosition()
                    if (position != RecyclerView.NO_POSITION) {
                        val note = adapter!!.getNotes().get(position)
                        val dialog = DeleteNoteDialog(requireContext(), object : OnDeleteListener {
                            override fun onConfirmDelete() {
                                viewModel.deleteNote(note)
                                adapter!!.notifyItemRemoved(position)
                            }

                            override fun onCancelDelete() {
                                adapter!!.notifyItemChanged(position)
                            }
                        })
                        dialog.show()
                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    RecyclerViewSwipeDecorator.Builder(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                        .addBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                com.github.dhaval2404.colorpicker.R.color.red_600
                            )
                        )
                        .addActionIcon(R.drawable.ic_trash_bin).create().decorate()
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            })
        itemTouchHelper.attachToRecyclerView(binding.rvNote)
    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): HomeFragmentBinding {
        return HomeFragmentBinding.inflate(inflater, container, false)
    }

    override fun clickView(view: View) {
        if (view.getId() == R.id.ivAdd) {
            navController.navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
    }

    private fun search() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterNote(newText)
                return true
            }
        })
    }

    private fun filterNote(newText: String) {
        val filteredList: MutableList<Note> = ArrayList()
        for (note in Objects.requireNonNull(viewModel.listNotes.getValue())!!) {
            if (note.note.lowercase(Locale.getDefault())
                    .contains(newText.lowercase(Locale.getDefault()))
            ) {
                filteredList.add(note)
            }
        }
        if (!filteredList.isEmpty()) {
            adapter!!.setNotes(filteredList)
        }
    }
}
