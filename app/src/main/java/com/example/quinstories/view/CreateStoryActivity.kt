package com.example.quinstories.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quinstories.databinding.ActivityCreateStoryBinding
import com.example.quinstories.viewmodel.CreateStoryViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class CreateStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateStoryBinding
    private lateinit var viewModel: CreateStoryViewModel
    private lateinit var mStorageRef: StorageReference
    private val CAMERA_REQUEST = 1888
    private val MY_CAMERA_PERMISSION_CODE = 100
    var theImage: Bitmap? = null
    var fileName: String? = null
    private lateinit var imageUri: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CreateStoryViewModel::class.java]
        viewModel.binding = binding

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST)

        binding.selectPicture.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
           // startActivityForResult(cameraIntent, CAMERA_REQUEST)
            resultLauncher.launch(cameraIntent)

        }

        viewModel.SuccessMessage.observe(this, Observer {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        })
        viewModel.failedMessage.observe(this) { it ->
            it?.let {
                Toast.makeText(this, "Please try after some time", Toast.LENGTH_SHORT).show()
            }
        }

        binding.submit.setOnClickListener {
            if(fileName!=null) {
                viewModel.saveData(fileName!!)
            }else{
                Toast.makeText(this, "upload image", Toast.LENGTH_SHORT).show()
            }
        }


    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val intent: Intent? = result.data
            var bitmp=intent?.extras!!["data"] as Bitmap?
            imageUri= getImageUri(this,bitmp)
            uploadPic()
            binding.picture.setImageBitmap(bitmp)

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
               // startActivityForResult(cameraIntent, CAMERA_REQUEST)
                resultLauncher.launch(cameraIntent)
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun uploadPic() {

        var riversRef: StorageReference? = null
        mStorageRef = FirebaseStorage.getInstance().getReference()
        //to create a separate folder with all the pictures uploaded

        fileName = imageUri.pathSegments.last()
        riversRef = mStorageRef.child("pictures/"+fileName )

        val uploadTask = riversRef.putFile(imageUri)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot -> // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                //var downloadUrl = taskSnapshot.
                //Log.d("downloadUrl", "" + downloadUrl)
        }

    }
    fun getImageUri(inContext: Context, inImage: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        inImage?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.getContentResolver(),
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }
}