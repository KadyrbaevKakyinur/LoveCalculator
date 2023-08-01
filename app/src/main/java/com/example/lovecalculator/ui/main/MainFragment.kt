package com.example.lovecalculator.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lovecalculator.LoveViewModel
import com.example.lovecalculator.Pref.Pref
import com.example.lovecalculator.R
import com.example.lovecalculator.databinding.FragmentMainBinding
import com.example.lovecalculator.di.db.LoveDao
import com.example.lovecalculator.remote.LoveModel
import com.geeks.lovecalculator.ui.history.HistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoveViewModel by viewModels()
    private var loveModel: LoveModel? = null

    @Inject
    lateinit var dao: LoveDao
    @Inject
    lateinit var pref: Pref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickers()

        if (!pref.isUserSeen()) {
            findNavController().navigate(R.id.onBoardingFragment)
        }

        loveModel = arguments?.getSerializable(HistoryAdapter.LOVE_MODEL_UPDATE_KEY) as LoveModel?

        if (loveModel != null) {
            initTextView()
        }
    }

    private fun initTextView() {
        with(binding) {
            etFistName.setText(loveModel!!.firstName)
            etSecondName.setText(loveModel!!.secondName)
            btnCalculate.text = "Update"
        }
    }

    private fun initClickers() {
        with(binding) {
            btnCalculate.setOnClickListener {
                viewModel.getLiveData(etFistName.text.toString(), etSecondName.text.toString())
                    .observe(this@MainFragment) {
                        if (loveModel == null) {
                            dao.insert(it)
                            findNavController().navigate(
                                R.id.resultFragment,
                                bundleOf(LOVE_MODEL_RESULT_KEY to it)
                            )
                        } else {
                            val data = loveModel?.copy(
                                firstName = it.firstName,
                                secondName = it.secondName,
                                result = it.result,
                                percentage = it.percentage,
                            )
                            dao.update(data!!)
                            findNavController().navigate(
                                R.id.resultFragment,
                                bundleOf(LOVE_MODEL_RESULT_KEY to data)
                            )
                        }
                    }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val LOVE_MODEL_RESULT_KEY = "LoveModelResult.key"
    }
}