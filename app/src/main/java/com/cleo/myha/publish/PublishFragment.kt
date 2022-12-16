package com.cleo.myha.publish

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cleo.myha.R
import com.cleo.myha.data.Category
import com.cleo.myha.databinding.FragmentPublishBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*


class PublishFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var viewModel: PublishViewModel
    private lateinit var binding: FragmentPublishBinding
    private var selectedImg: Uri? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPublishBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this)[PublishViewModel::class.java]

        firebaseStorage = FirebaseStorage.getInstance()

        binding.btnPublish.setOnClickListener {

            val tag = when (binding.chipGroup.checkedChipId) {
                R.id.chip1 -> Category.Health.type
                R.id.chip2 -> Category.Workout.type
                R.id.chip3 -> Category.Reading.type
                R.id.chip4 -> Category.Learning.type
                R.id.chip5 -> Category.General.type
                else -> {
                    ""
                }
            }

            val imageRoot = selectedImg.toString()
            Log.d("LOUIS", "Image $imageRoot")

            val formatter = SimpleDateFormat(
                FORMATTER_TIME_PATTERN, Locale.getDefault()
            )
            val now = Date()
            val fileName = formatter.format(now)
            val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

            val photoUri = FirebaseStorage.getInstance().reference.child("images/$fileName.jpg")


            viewModel.setPost(
                binding.textPostTitle.text.toString(),
                binding.textPostContent.text.toString(),
                tag,
                photoUri.toString()
            )
            uploadData(fileName)
            findNavController().navigateUp()
        }

        // 開啟相簿
        binding.btnSelected.setOnClickListener {
            val gallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, PICTURE_FROM_GALLERY)
            selectImage()
        }

        viewModel.photo.observe(
            viewLifecycleOwner
        ) {
            binding.imageUpload.setImageURI(selectedImg)
        }

        return binding.root
    }

    private fun selectImage() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, INTENT_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == INTENT_CODE && resultCode == Activity.RESULT_OK) {

            selectedImg = data?.data!!

            viewModel.displayPhoto(selectedImg!!)
        }
    }

    private fun uploadData(fileName: String) {

        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName.jpg")

        selectedImg?.let {
            storageReference.putFile(it)
                .addOnSuccessListener { Task ->

                    Log.d("Louis ", "Task ${Task.task}")
                }
                .addOnFailureListener {
                    Log.d("MFG", "FAILED")
                }
        }
    }

    // 詢問user權限
    fun askPermissionPhoto() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            0
        )
    }

    companion object {
        const val PICTURE_FROM_GALLERY = 1001
        const val FORMATTER_TIME_PATTERN = "yyyy_MM_dd_HH_mm_ss"
        const val INTENT_CODE = 1

    }
}

