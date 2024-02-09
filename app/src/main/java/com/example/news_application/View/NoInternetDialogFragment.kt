package com.example.news_application.View

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.news_application.R
import com.google.android.material.button.MaterialButton

class NoInternetDialogFragment(private val retryClickListener: () -> Unit) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_no_internet_dialog, container, false)
        val retryButton: MaterialButton = view.findViewById(R.id.retryBtn)

        retryButton.setOnClickListener {
            retryClickListener.invoke()
            // Dismiss the dialog
            dismiss()
        }

        return view
    }
}

