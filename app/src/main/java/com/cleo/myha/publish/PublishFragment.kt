package com.cleo.myha.publish

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
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
import com.cleo.myha.databinding.FragmentPublishBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

class PublishFragment : Fragment() {

    companion object {
        const val PICTUREFROMGALLERY = 1001
        const val PICTUREFROMCAMERA = 1002
    }

    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseStorage: FirebaseStorage

    private var selectedImg: Uri? = null
    private var selectedBitMap: Bitmap? = null
    private val storage = Firebase.storage.reference
    private lateinit var photoFileName: String
    private lateinit var viewModel: PublishViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPublishBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this)[PublishViewModel::class.java]

        firebaseStorage = FirebaseStorage.getInstance()

        binding.btnPublish.setOnClickListener {
            val tag = when (binding.chipGroup.checkedChipId) {
                R.id.chip1 -> "health"
                R.id.chip2 -> "workout"
                R.id.chip3 -> "reading"
                R.id.chip4 -> "learning"
                R.id.chip5 -> "general"
                else -> {
                    ""
                }
            }

            val imageRoot = selectedImg.toString()
            Log.d("LOUIS", "Image $imageRoot")

            val formatter = SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss", Locale.getDefault()
            )
            val now = Date()
            val fileName = formatter.format(now)
            val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

            val photoUri = FirebaseStorage.getInstance().reference.child("images/$fileName.jpg")
            Log.d("LOUIS", "photoUri $photoUri")

            addData(
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
            startActivityForResult(gallery, PICTUREFROMGALLERY)
            selectImage()
        }

        viewModel.photo.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {

                binding.imageUpload.setImageURI(selectedImg)
            }
        )

//        val localFile = File.createTempFile("tempImage", "jpg")
//
//        val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)

        return binding.root
    }

    private fun selectImage() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            selectedImg = data?.data!!

            viewModel.displayPhoto(selectedImg!!)
        }
    }

    fun uploadData(fileName: String) {

//        val progressDialog = ProgressDialog(requireContext())
//        progressDialog.setMessage("Uploading File ...")
//        progressDialog.setCancelable(false)
//        progressDialog.show()

//        val formatter = SimpleDateFormat(
//            "yyyy_MM_dd_HH_mm_ss", Locale.getDefault()
//        )
//        val now = Date()
//        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName.jpg")

        selectedImg?.let {
            storageReference.putFile(it)
                .addOnSuccessListener { Task ->
//                    Toast.makeText(context, "Successfully Uploaded", Toast.LENGTH_SHORT).show()
//                    if(progressDialog.isShowing) progressDialog.dismiss()
                    Log.d("Louis ", "Task ${Task.task}")
                }
                .addOnFailureListener {
//                    if(progressDialog.isShowing) progressDialog.dismiss()
                    Log.d("MFG", "FAILED")
                }
        }
    }

    // 詢問user權限
    fun permissionPhoto() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
//                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            0
        )
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
        return format.format(date)
    }

    fun addData(title: String, content: String, tag: String, Uri: String) {
        val articles = FirebaseFirestore.getInstance().collection("posts")
        val postId = articles.document().id
        val authorEmail = auth.currentUser?.let {
            it.email
        }

        val createdTime = Date().time

        val lastUpdatedTime = convertLongToTime(createdTime)
        val data = hashMapOf(
            "title" to title,
            "content" to content,
            "id" to postId,
            "author" to authorEmail,
            "tag" to tag,
            "lastUpdatedTime" to lastUpdatedTime,
            "photo" to Uri

        )

        db.collection("posts")
            .document(postId)
            .set(data)
            .addOnSuccessListener {
                Log.d("Cleooo", "Success!!")
            }
            .addOnFailureListener { e ->
                Log.d("Cleooo", "add fail")
            }
    }
}
