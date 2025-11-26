package com.duc.beproductivekotlin.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.duc.beproductivekotlin.CommonUtils
import com.duc.beproductivekotlin.R
import com.duc.beproductivekotlin.view.fragment.ProfileFragment.Companion.PHOTO_PROFILE

class ProfileDialog(context: Context, private val listener: OnDeleteListener) : Dialog(context) {
    interface OnDeleteListener {
        fun onSave(name: String, email: String, photoUri: Uri?)
        fun onChosePhoto()
    }

    private var photoUri: Uri? = null
    private lateinit var ivPhoto: ImageView
    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_dialog)

        if (window != null) {
            window!!.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        }
        initViews()
    }

    private fun initViews() {
        val btSave = findViewById<Button>(R.id.btSave)
        val btCancel = findViewById<TextView>(R.id.btCancel)
        ivPhoto = findViewById(R.id.ivPhoto)
        edtName = findViewById(R.id.edtName)
        edtEmail = findViewById(R.id.edtEmail)
        edtName.setText(CommonUtils.instance?.getPref("name"))
        edtEmail.setText(CommonUtils.instance?.getPref("email"))

        btCancel.setOnClickListener { dismiss() }
        btSave.setOnClickListener { save() }
        ivPhoto.setOnClickListener { listener.onChosePhoto() }

        CommonUtils.instance?.getPref(PHOTO_PROFILE)?.let { savedUri ->
            Glide.with(context).load(savedUri.toUri()).circleCrop().into(ivPhoto)
        }
    }

    private fun save() {
        if (edtName.text.toString().isEmpty() || edtEmail.text.toString().isEmpty()) {
            showToast("Vui lòng điền đầy đủ thông tin!")
            return
        }
        listener.onSave(edtName.text.toString(), edtEmail.text.toString(), photoUri)
        dismiss()
    }

    fun setPhoto(uri: Uri) {
        photoUri = uri
        Glide.with(context).load(uri).circleCrop().into(ivPhoto)
    }

    private fun showToast(message: String) {
        val layout = layoutInflater.inflate(R.layout.custom_toast, null)
        val tvMessage = layout.findViewById<TextView>(R.id.tvMessage)
        tvMessage.text = message

        Toast(context).apply {
            duration = Toast.LENGTH_SHORT
            view = layout
            show()
        }
    }
}
