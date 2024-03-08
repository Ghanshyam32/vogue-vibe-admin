package com.ghanshyam.voguevibeadmin

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ghanshyam.voguevibeadmin.databinding.ActivityMainBinding
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.kotlin.colorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val selectedImages = mutableListOf<Uri>()
    private val selectedColors = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonColorPicker.setOnClickListener {
            ColorPickerDialog.Builder(this).setTitle("Product color")
                .setPositiveButton("SELECT", object : ColorEnvelopeListener {
                    override fun onColorSelected(envelope: ColorEnvelope?, fromUser: Boolean) {
                        envelope?.let {
                            selectedColors.add(it.color)
                            updateColors()
                        }
                    }

                })
                .setNegativeButton("CANCEL")
                { colorPicker, _ ->
                    colorPicker.dismiss()
                }.show()
        }

    }

    private fun updateColors() {
        var colors = ""
        selectedColors.forEach {
            colors = "$colors ${Integer.toHexString(it)}"
        }
        binding.tvSelectedColors.text = colors
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.saveProduct) {
            val productValidation = validateInformation()
            if (!productValidation) {
                Toast.makeText(this, "Check your inputs", Toast.LENGTH_SHORT).show()
                return false;
            }
            saveProduct()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveProduct() {
        val name = binding.edName.text.toString().trim()
        val category = binding.edCategory.text.toString().trim()
        val price = binding.edPrice.text.toString().trim()
        val discount = binding.offerPercentage.text.toString().trim()
        val description = binding.edDescription.text.toString().trim()

        val sizes = getSizesList(binding.edSizes.text.toString().trim())
    }

    private fun getSizesList(sizes: String): List<String>? {
        if (sizes.isEmpty())
            return null
        val sizeList = sizes.split(",")
        return sizeList

    }

    private fun validateInformation(): Boolean {

        if (binding.edPrice.text.toString().trim().isEmpty())
            return false;
        if (binding.edName.text.toString().trim().isEmpty())
            return false;
        if (binding.edCategory.text.toString().trim().isEmpty())
            return false;
        if (selectedImages.isEmpty())
            return false

        return true
    }

}