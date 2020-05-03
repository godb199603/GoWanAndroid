package com.example.gowanandroid.ext

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment



fun Fragment.openInExplorer(link: String?) {
    startActivity(Intent().apply {
        action = Intent.ACTION_VIEW
        data = Uri.parse(link)
    })
}