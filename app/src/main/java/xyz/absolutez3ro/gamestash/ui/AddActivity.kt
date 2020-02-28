package xyz.absolutez3ro.gamestash.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import xyz.absolutez3ro.gamestash.R
import xyz.absolutez3ro.gamestash.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    private fun setupViews() {
        val inputName = binding.inputGameName
        val inputLayoutName = binding.inputLayoutGameName
        val inputDesc = binding.inputGameDesc
        val inputLayoutDesc = binding.inputLayoutGameDesc
        val ratingBar = binding.ratingBar
        val save = binding.saveExtendedFab

        inputName.setOnClickListener {
            inputLayoutName.error = null
        }
        inputName.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) inputLayoutName.error = null
        }
        inputDesc.setOnClickListener {
            inputLayoutDesc.error = null
        }
        inputDesc.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) inputLayoutDesc.error = null
        }

        save.setOnClickListener {
            val replyIntent = Intent()
            if (!EmptyTextFields(inputName, inputLayoutName, inputDesc, inputLayoutDesc)) {
                val bundle = Bundle().apply {
                    putString(NAME, inputName.text.toString())
                    putString(DESC, inputDesc.text.toString())
                    putInt(RATING, ratingBar.rating.toInt())
                }
                replyIntent.putExtra(EXTRA_REPLY, bundle)
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
        }
    }

    private fun EmptyTextFields(
        inputName: TextInputEditText,
        inputLayoutName: TextInputLayout,
        inputDesc: TextInputEditText,
        inputLayoutDesc: TextInputLayout
    ): Boolean {
        var isEmpty = false
        if (inputName.text!!.isEmpty()) {
            inputLayoutName.error = getString(R.string.error_name)
            isEmpty = true
        } else inputLayoutName.error = null

        if (inputDesc.text!!.isEmpty()) {
            inputLayoutDesc.error = getString(R.string.error_desc)
            isEmpty = true
        } else inputLayoutDesc.error = null

        return isEmpty
    }

    companion object {
        const val EXTRA_REPLY = "xyz.absolutez3ro.gamestash.REPLY"
        const val NAME = "GAME_NAME"
        const val DESC = "GAME_DESC"
        const val RATING = "GAME_RATING"
    }
}