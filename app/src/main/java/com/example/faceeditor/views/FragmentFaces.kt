package com.example.faceeditor.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.faceeditor.App
import com.example.faceeditor.R
import com.example.faceeditor.database.FilterOutput
import com.example.faceeditor.viewModels.TaskType
import com.example.faceeditor.viewModels.TaskViewModel
import com.example.faceeditor.viewModels.TaskViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class FragmentFaces : Fragment(), FragmentFilter.ItemClickListener {
    // TODO: Rename and change types of parameters
    private val scope = MainScope()
    private lateinit var viewModel: TaskViewModel
    private val faceItems = ArrayList<FilterOutput>()

    private val viewModelFactory by lazy { TaskViewModelFactory(App.dbManger, App.remoteManager) }
    private lateinit var recyclerView: RecyclerView

    private var toast: Toast? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)
        viewModel.contactsLiveData.observe(viewLifecycleOwner, Observer { data ->

            faceItems.clear()
            faceItems.addAll(data)
            recyclerView.adapter?.notifyDataSetChanged()
        })

        viewModel.dataLoading.observe(viewLifecycleOwner, Observer {

            toast?.cancel()
            val text = if (it) {"Loading..."} else { "Done" }
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
            toast?.show()
        })
    }

    override fun onResume() {
        super.onResume()

        activity?.topAppBar?.title = "Faces"
    }

    override fun onDestroyView() {
        super.onDestroyView()

        toast?.cancel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_faces, container, false)
        recyclerView = rootView.findViewById(R.id.faceCollectView)
        val gridLayoutManager = GridLayoutManager(activity, 3)
        val adapterFaceItems = AdapterFaceItems(inflater.context, faceItems)
        recyclerView.adapter = adapterFaceItems
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.layoutAnimation = AnimationUtils.loadLayoutAnimation(inflater.context, R.anim.recycle_view_animation)


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fab = activity?.findViewById<FloatingActionButton>(R.id.floatingBtn)
        fab?.setOnClickListener {
//            var navigation = findNavController()
//            var bundle = Bundle()

//            navigation.navigate(R.id.action_fragmentFaces_to_bottomSheet)
//            navigation


            scope.launch {

                val a= App.remoteManager.getAllMembers()
                val b= App.remoteManager.getMember(1)
            }
            val fragmentFilter = FragmentFilter(this)
            activity?.let { fragmentFilter.show(it.supportFragmentManager, fragmentFilter.tag) }
        }
    }

    private fun getTaskList(type: TaskType){

        scope.launch {

            viewModel.getTasks(type)
        }
    }

    override fun onItemClick(item: String?) {

        getTaskList(TaskType.All)
    }

}
