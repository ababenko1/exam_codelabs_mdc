package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.shr_login_fragment.password_input
import kotlinx.android.synthetic.main.shr_login_fragment.password_text
import kotlinx.android.synthetic.main.shr_login_fragment.view.next_button
import kotlinx.android.synthetic.main.shr_login_fragment.view.password_text

/**
 * Fragment representing the login screen for Shrine.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        // Snippet from "Navigate to the next Fragment" section goes here.

        val view = inflater.inflate(R.layout.shr_login_fragment, container, false)

        view.next_button.setOnClickListener {
            if (!isPasswordValid(password_text.text)) {
                password_input.error = getString(R.string.shr_error_password)
            } else {
                password_input.error = null
                (activity as NavigationHost).navigateTo(ProductGridFragment(), false)
            }
        }

        view.password_text.setOnKeyListener { _, _, _ ->
            if (isPasswordValid(password_text.text)) {
                password_input.error = null
            }
            false
        }

        return view
    }

    private fun isPasswordValid(text: Editable?): Boolean {
       return text != null && text.length >= 8
    }
}
