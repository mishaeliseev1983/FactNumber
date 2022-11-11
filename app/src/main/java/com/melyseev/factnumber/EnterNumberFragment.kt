package com.melyseev.factnumber

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

private const val ARG_PARAM1 = "PARAM1"

class EnterNumberFragment : Fragment() {
    private var param1: String? = null
    private var showDetails: ShowDetails? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        showDetails = context as ShowDetails
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_enter_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_getfact).setOnClickListener {
            showDetails?.showDetails()
        }
    }

    override fun onDetach() {
        super.onDetach()
        showDetails = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            EnterNumberFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    interface ShowDetails{
        fun showDetails()
    }
}