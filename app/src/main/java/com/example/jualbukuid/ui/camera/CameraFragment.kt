package com.example.jualbukuid.ui.camera

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jualbukuid.R
import com.example.jualbukuid.databinding.CameraFragmentBinding
import com.example.jualbukuid.ui.CameraActivity

class CameraFragment : Fragment() {

    companion object {
        fun newInstance() = CameraFragment()
    }

    private var _binding: CameraFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CameraFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CameraFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGotoCamera.setOnClickListener {
            activity?.let {
                val intent = Intent(it, CameraActivity::class.java)
                it.startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CameraFragmentViewModel::class.java)

    }

    fun uploadData(){

    }

}