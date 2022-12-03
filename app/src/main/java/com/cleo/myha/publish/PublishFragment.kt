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
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cleo.myha.R
import com.cleo.myha.databinding.FragmentPublishBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
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
    private val storage = Firebase.storage.reference

    private lateinit var imageView: ImageView
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPublishBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        val viewModel = ViewModelProvider(this)[PublishViewModel::class.java]

//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(intent, 1);

//        viewModel.postData.observe(viewLifecycleOwner, Observer {
//
//        })


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
            addData(
                binding.textPostTitle.text.toString(),
                binding.textPostContent.text.toString(),
                tag
            )
            findNavController().navigateUp()

            //開啟相簿
            binding.btnUpload.setOnClickListener {
                val gallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(gallery, PICTUREFROMGALLERY)
                permissionPhoto()
            }

        }

        return binding.root
    }

    //詢問user權限
    fun permissionPhoto() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
//                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ), 0
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICTUREFROMGALLERY) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }

    }


    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
        return format.format(date)
    }

    fun addData(title: String, content: String, tag: String) {
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
            "lastUpdatedTime" to lastUpdatedTime
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


