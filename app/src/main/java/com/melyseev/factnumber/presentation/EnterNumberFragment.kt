package com.melyseev.factnumber.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.melyseev.factnumber.R
import com.melyseev.factnumber.presentation.recycler.NumbersAdapter
import javax.inject.Inject

private const val ARG_PARAM1 = "PARAM1"

class EnterNumberFragment : Fragment() {
    private var param1: String? = null
    private var showDetails: ShowDetails? = null


    private val daggerApplicationComponent by lazy {
        (requireActivity().application as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModuleFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[EnterNumberViewModel::class.java]
    }

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
        daggerApplicationComponent.inject(this)
        return inflater.inflate(R.layout.fragment_enter_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


              // view.findViewById<Button>(R.id.factButton).setOnClickListener {
       //     showDetails?.showDetails()
       // }

        val progressBar = view.findViewById<ProgressBar>(R.id.progress)
        val factButton = view.findViewById<Button>(R.id.factButton)
        val randomButton = view.findViewById<Button>(R.id.randomFactButton)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val inputLayout = view.findViewById<TextInputLayout>(R.id.textInputLayout)
        val inputEditText = view.findViewById<TextInputEditText>(R.id.inputEditText)


        randomButton.setOnClickListener {
            viewModel.fetchRandomNumber()
        }

        factButton.setOnClickListener {
            viewModel.fetchAboutNumber(inputEditText.text.toString())
        }

        inputEditText.addTextChangedListener( object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun afterTextChanged(p0: Editable?) {

                viewModel.clearError()
                //inputLayout.isErrorEnabled = false
                //inputLayout.error = ""
            }
        })

        viewModel.observeProgress(this){
            if(it)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
        }

        viewModel.observeState(this){
            it.apply(layout = inputLayout, editText = inputEditText)
        }

        val adapter = NumbersAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)
        viewModel.observeNumberList(this){
            adapter.change( it )
        }


        viewModel.init( savedInstanceState == null)
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