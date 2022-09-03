package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter = MainListAdapter( MainListAdapter.OnClickListener {
            viewModel.displayDetails(it)
            Toast.makeText(this.context, "you just pressed ${it.id} and the code name is ${it.codename}", Toast.LENGTH_SHORT).show()
        })
//        adapter.submitList(viewModel.dumList)

        viewModel.showDetail.observe(viewLifecycleOwner, Observer {
            if(it != null)
            {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.finsNav()
            }

        })


        binding.asteroidRecycler.adapter= adapter

        viewModel.listData.observe(viewLifecycleOwner,Observer{
            adapter.submitList(it)
        })


        viewModel.response.observe(viewLifecycleOwner,Observer{
            binding.testTxt.text=it
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }



}
