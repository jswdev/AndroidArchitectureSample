package com.example.faceeditor.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.faceeditor.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentLogs.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentLogs : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("123123", "FragmentLogs on create")

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val fab = activity?.findViewById<FloatingActionButton>(R.id.floatingBtn)
        fab?.setOnClickListener {

            toast?.cancel()
            toast = Toast.makeText(context, "log", Toast.LENGTH_SHORT)
            toast?.show()
        }
    }

    override fun onResume() {
        super.onResume()

        Log.d("123123", "FragmentLogs on resume")

        activity?.topAppBar?.title = "Logs"
    }

    override fun onStop() {
        super.onStop()

        Log.d("123123", "FragmentLogs on onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("123123", "FragmentLogs on distory")
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
        return inflater.inflate(R.layout.fragment_logs, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentLogs.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentLogs().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
