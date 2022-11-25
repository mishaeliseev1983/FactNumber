package com.melyseev.factnumber.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.melyseev.factnumber.R
import com.melyseev.factnumber.data.NumberRepositoryBase
import com.melyseev.factnumber.domain.NumberInteractor
import com.melyseev.factnumber.domain.NumberRepository
import com.melyseev.factnumber.presentation.EnterNumberViewModel

private const val ARG_PARAM1 = "PARAM1"

class EnterNumberFragment : Fragment() {
    private var param1: String? = null
    private var showDetails: ShowDetails? = null

    private lateinit var viewModel: EnterNumberViewModel

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

       // view.findViewById<Button>(R.id.factButton).setOnClickListener {
       //     showDetails?.showDetails()
       // }

        val progressBar = view.findViewById<ProgressBar>(R.id.progress)
        val factButton = view.findViewById<Button>(R.id.factButton)
        val randomButton = view.findViewById<Button>(R.id.randomFactButton)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val inputEditText = view.findViewById<EditText>(R.id.inputEditText)




        val numberInteractor =
            NumberInteractor.Base()

        val numberCommunication =
            NumberCommunication.Base(
                progressCommunication = ProgressCommunication.Base(),
                numberStateCommunication = NumberStateCommunication.Base(),
                numbersListCommunication = NumbersListCommunication.Base())

        //viewModel = EnterNumberViewModel(communication = numberCommunication)

        viewModel.observeProgress(this){
            if(it)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
        }

        viewModel.observeState(this){

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