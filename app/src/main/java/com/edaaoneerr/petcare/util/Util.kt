package com.edaaoneerr.petcare.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.edaaoneerr.petcare.R
import java.util.regex.Pattern

fun ImageView.gorselIndir(url: String?, placeholder: CircularProgressDrawable) {
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun String.isValidPhoneNumber(): Boolean {
    val patterns =
        "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$"
    return Pattern.compile(patterns).matcher(this).matches()
}

fun placeholderYap(context: Context): CircularProgressDrawable {

    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()

    }

}

fun navigate(view: View, action: NavDirections) {
    Navigation.findNavController(view).navigate(action)
}


@BindingAdapter("android:XMLGorseliIndir")
fun XMLGorselIndir(view: ImageView, url: String?) {

    view.gorselIndir(url, placeholderYap(view.context))

}