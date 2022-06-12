package com.example.jualbukuid.ui.account

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.jualbukuid.R
import com.example.jualbukuid.databinding.AccountFragmentBinding
import com.example.jualbukuid.databinding.CameraFragmentBinding
import com.example.jualbukuid.models.LoginViewModel
import com.example.jualbukuid.models.UserPreference
import com.example.jualbukuid.models.UserViewModel
import com.example.jualbukuid.models.ViewModelFactory
import com.example.jualbukuid.ui.CameraActivity
import com.example.jualbukuid.ui.LoginActivity
import com.example.jualbukuid.ui.LogoutActivity

//public val Context.data: DataStore<Preferences> by preferencesDataStore(name = "user")

class AccountFragment : Fragment() {

    companion object {
        fun newInstance() = AccountFragment()
    }

    private var _binding: AccountFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AccountViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AccountFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            val logout = Intent(requireActivity(),LogoutActivity::class.java)
            logout.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logout)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        // TODO: Use the ViewModel
    }

}