package hu.bme.aut.android.gaminggoods.feature.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.gaminggoods.R
import hu.bme.aut.android.gaminggoods.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {
    private lateinit var binding : FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    //JCIRJL
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var platform: String? = null

        activity?.title = getString(R.string.app_name)

        binding.btnAll.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToDealsFragment(R.string.all,platform)
            findNavController().navigate(action)
        }
        binding.btnPlayStation4.setOnClickListener {
            platform = "ps4"
            val action = MenuFragmentDirections.actionMenuFragmentToDealsFragment(R.string.playstation4,platform)
            findNavController().navigate(action)
        }
        binding.btnPlayStation5.setOnClickListener {
            platform = "ps5"
            val action = MenuFragmentDirections.actionMenuFragmentToDealsFragment(R.string.playstation5,platform)
            findNavController().navigate(action)
        }
        binding.btnPC.setOnClickListener{
            platform = "pc"
            val action = MenuFragmentDirections.actionMenuFragmentToDealsFragment(R.string.pc,platform)
            findNavController().navigate(action)
        }
        binding.btnXboxOne.setOnClickListener {
            platform = "xbox-one"
            val action = MenuFragmentDirections.actionMenuFragmentToDealsFragment(R.string.xboxone,platform)
            findNavController().navigate(action)
        }
        binding.btnXboxSeries.setOnClickListener {
            platform = "xbox-series-xs"
            val action = MenuFragmentDirections.actionMenuFragmentToDealsFragment(R.string.xboxseries,platform)
            findNavController().navigate(action)
        }
        binding.btnAndroid.setOnClickListener {
            platform = "android"
            val action = MenuFragmentDirections.actionMenuFragmentToDealsFragment(R.string.android,platform)
            findNavController().navigate(action)
        }
        binding.btnIos.setOnClickListener {
            platform = "ios"
            val action = MenuFragmentDirections.actionMenuFragmentToDealsFragment(R.string.ios,platform)
            findNavController().navigate(action)
        }
        binding.btnSwitch.setOnClickListener {
            platform = "switch"
            val action = MenuFragmentDirections.actionMenuFragmentToDealsFragment(R.string.nintendoswitch,platform)
            findNavController().navigate(action)
        }
        binding.btnVR.setOnClickListener {
            platform = "vr"
            val action = MenuFragmentDirections.actionMenuFragmentToDealsFragment(R.string.vr,platform)
            findNavController().navigate(action)
        }
    }
}
