package com.example.jualbukuid.ui.homepage

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bumptech.glide.Glide
import com.example.jualbukuid.databinding.HomeFragmentBinding
import com.example.jualbukuid.models.*

private val Context.data: DataStore<Preferences> by preferencesDataStore(name = "user")

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var sViewModel: SharedViewModel
    private lateinit var pref: UserPreference
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        sViewModel = ViewModelProvider(
//            this, ViewModelFactory(UserPreference.getInstance(data), requireActivity())
//        )[SharedViewModel::class.java]
//        sViewModel.getUser().observe(viewLifecycleOwner){
//            if (it != null){
//                Log.d("Nama User", user.name)
//                Log.d("Phone User", user.phone)
//            }
//        }
//        viewModel = HomeViewModel()
//        Log.d("Nama User", user.name)
//        Log.d("Phone User", user.phone)
//        viewModel.getDetailUser().observe(viewLifecycleOwner){
//            if(it!=null){
//                binding.apply {
//                    Log.d("Cek Nama", it.name)
//                    Log.d("Cek Phone", it.phone)
//                    tvNamaPengguna.text = it.name
//                    tvHpPengguna.text = it.phone
//                }
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

    }



}