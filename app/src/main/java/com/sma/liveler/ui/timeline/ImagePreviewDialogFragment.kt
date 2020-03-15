package com.sma.liveler.ui.timeline


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.sma.liveler.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image_preview_dialog.*
import java.io.File


/**
 * A simple [Fragment] subclass.
 */
class ImagePreviewDialogFragment : DialogFragment() {

    private var status = "";
    private lateinit var fileName: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val width = ConstraintLayout.LayoutParams.MATCH_PARENT
        val height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

    override fun onResume() {
        super.onResume()
        val width = ConstraintLayout.LayoutParams.MATCH_PARENT
        val height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_preview_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvStatus.text = status
        Picasso.get().load(fileName)
            .placeholder(R.drawable.ic_user_avtar)
            .into(ivImage)
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun setFileName(fileName: File) {
        this.fileName = fileName
    }

}
