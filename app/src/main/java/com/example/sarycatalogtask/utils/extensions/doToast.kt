package com.example.sarycatalogtask.utils.extensions



import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * For fragments
 */
fun Fragment.doToast(message: String) {
    this.requireActivity().apply {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

/**
 * For activities
 */
fun AppCompatActivity.doToast(message: String) {
    this.apply {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

/**
 * For view
 */
fun View.doToast(message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}