package com.example.quinstories.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quinstories.adapter.StoryListAdapter
import com.example.quinstories.databinding.ActivityMainBinding
import com.example.quinstories.model.Story
import com.example.quinstories.viewmodel.MainActivityViewModel
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    lateinit var storyAdapter: StoryListAdapter
    var isLoading:Boolean = false
    var key: String? = null
    lateinit var databaseReference:DatabaseReference
    var storieslist:ArrayList<Story> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.binding = binding

        storyAdapter = StoryListAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = storyAdapter

        databaseReference=FirebaseDatabase.getInstance().reference.child("stories")

        binding.fab.setOnClickListener {
            val intent = Intent(this, CreateStoryActivity::class.java)
            startActivity(intent)
        }

        getData()


        /*viewModel.SuccessMessage.observe(this, Observer {
            storyAdapter.setMenuList(it)
        })
        viewModel.failedMessage.observe(this) { it ->
            it?.let {
                Toast.makeText(this, "Please try after some time", Toast.LENGTH_SHORT).show()
            }
        }*/


        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
               var linearlayoutManager = recyclerView.layoutManager as LinearLayoutManager
                var totalItem = linearlayoutManager?.itemCount
                var lastvisible = linearlayoutManager.findLastCompletelyVisibleItemPosition()
                if(totalItem!! <lastvisible+3){
                    if(!isLoading){
                        isLoading = true
                        getData()
                    }

                }
            }
        })
    }

    fun getData() {
        binding.loading.visibility = View.VISIBLE
       getQuery(key)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    binding.loading.visibility = View.GONE
                    if (dataSnapshot.exists()) {
                        for (dsp in dataSnapshot.children) {
                            val story: Story? = dsp.getValue(Story::class.java)
                            println("sdkdsk "+story)
                            storieslist.add(story!!)
                            key = dsp.key
                        }
                       // SuccessMessage.postValue(storieslist)
                        storyAdapter.setMenuList(storieslist)
                        isLoading=false
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    binding.loading.visibility = View.GONE
                }
            })

    }

    fun getQuery(value: String?): Query {

        if(value == null){

            return databaseReference.orderByKey().limitToFirst(10)
        }
        return databaseReference.orderByKey().startAfter(value).limitToFirst(10)
    }



}