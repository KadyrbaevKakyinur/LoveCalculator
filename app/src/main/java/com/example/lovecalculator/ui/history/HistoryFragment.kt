package com.example.lovecalculator.ui.history

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lovecalculator.LoveViewModel
import com.example.lovecalculator.R
import com.example.lovecalculator.databinding.FragmentHistoryBinding
import com.geeks.lovecalculator.ui.history.HistoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoveViewModel by viewModels()
    private val adapter = HistoryAdapter(arrayListOf(),this::onItemClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        initView()
    }

    private fun initView() {
        with(binding){
            viewModel.getAllAlphabetically().observe(this@HistoryFragment){
                adapter.list = it
            }
        }
    }

    private fun onItemClick(bundle: Bundle){
        val dialogBuilder = AlertDialog.Builder(requireActivity())
        dialogBuilder.setTitle("Вы хотите обновить данные?")
            .setMessage("Востановить придедущие данные не бедет возможным!")
            .setPositiveButton("ОК") { dialog: DialogInterface, _: Int ->
                // Обработка нажатия кнопки "OK"
                findNavController().navigate(R.id.mainFragment,bundle)
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog: DialogInterface, _: Int ->
                // Обработка нажатия кнопки "Отмена"
                dialog.dismiss()
            }
        dialogBuilder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}