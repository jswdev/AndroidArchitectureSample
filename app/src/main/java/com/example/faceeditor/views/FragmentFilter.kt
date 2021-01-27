package com.example.faceeditor.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.faceeditor.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentFilter.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentFilter(private val mListener: ItemClickListener? = null) : BottomSheetDialogFragment() {

    val TAG = "ActionBottomDialog"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.textView).setOnClickListener {
            onClick(it)
        }
        view.findViewById<View>(R.id.textView2).setOnClickListener{
            onClick(it)
        }
        view.findViewById<View>(R.id.textView3).setOnClickListener{
            onClick(it)
        }
        view.findViewById<View>(R.id.textView4).setOnClickListener{
            onClick(it)
        }
    }

    fun onClick(view: View) {
        val tvSelected = view as TextView
        mListener?.onItemClick(tvSelected.text.toString())
        dismiss()
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        mListener = if (context is ItemClickListener) {
//            context
//        } else {
//            throw RuntimeException(
//                context.toString()
//                    .toString() + " must implement ItemClickListener"
//            )
//        }
//    }

    interface ItemClickListener {
        fun onItemClick(item: String?)
    }

}