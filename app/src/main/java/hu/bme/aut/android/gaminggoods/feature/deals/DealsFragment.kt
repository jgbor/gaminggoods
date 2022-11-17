package hu.bme.aut.android.gaminggoods.feature.deals

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.gaminggoods.R
import hu.bme.aut.android.gaminggoods.databinding.FragmentDealsBinding
import hu.bme.aut.android.gaminggoods.model.DealData
import hu.bme.aut.android.gaminggoods.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DealsFragment : Fragment(), DealsAdapter.OnDealSelectedListener, DealDataArrayHolder {
    private lateinit var binding: FragmentDealsBinding
    private var dealDataArray: Array<DealData?>? = null
    private lateinit var adapter: DealsAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var category: String? = null
    private var sortBy: String? = null
    private var type: String? = null
    private val args: DealsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDealsBinding.inflate(layoutInflater)

        swipeRefreshLayout = binding.swipeRefresh
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            loadDealData()
        }

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_deals, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                Log.i("MENU",menuItem.itemId.toString())
                return when (menuItem.itemId) {
                    R.id.sortByValue -> {
                        sortBy = "value"
                        loadDealData()
                        true
                    }
                    R.id.sortByDate -> {
                        sortBy = "date"
                        loadDealData()
                        true
                    }
                    R.id.sortByPopularity -> {
                        sortBy = "popularity"
                        loadDealData()
                        true
                    }
                    R.id.type_game -> {
                        type = "game"
                        loadDealData()
                        true
                    }
                    R.id.type_loot -> {
                        type = "loot"
                        loadDealData()
                        true
                    }
                    R.id.reset -> {
                        type = null
                        sortBy = null
                        loadDealData()
                        true
                    }
                    else -> false
                }
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)
        menuHost.invalidateMenu()

        category = args.category
        activity?.title = getString(args.titleId)

        return binding.root
    }

    override fun onResume(){
        super.onResume()
        loadDealData()
    }

    private fun initRecyclerView() {
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = DealsAdapter(this, this)
        dealDataArray?.forEach { d -> adapter.addDeal(d) }
        binding.mainRecyclerView.adapter = adapter
    }

    override fun onDealSelected(deal: DealData?) {
        val action = DealsFragmentDirections.actionDealsFragmentToDetailsFragment(deal)
        findNavController().navigate(action)
    }

    override fun getDealDataList(): Array<DealData?>? {
        return dealDataArray
    }

    private fun loadDealData() {
        NetworkManager.getDeals(category,sortBy,type)?.enqueue(object : Callback<Array<DealData?>?> {
            override fun onResponse(
                call: Call<Array<DealData?>?>,
                response: Response<Array<DealData?>?>
            ) {
                Log.d("DealsFragment", "onResponse: " + response.code())
                if (response.isSuccessful) {
                    displayDealsData(response.body())
                } else {
                    Snackbar.make(this@DealsFragment.requireView(), "Error: " + response.message(), Snackbar.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<Array<DealData?>?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                type = null
                sortBy = null
                Snackbar.make(this@DealsFragment.requireView(), getString(R.string.nothing_found), Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun displayDealsData(receivedDealsData: Array<DealData?>?){
        dealDataArray = receivedDealsData
        initRecyclerView()
    }
}