package co.id.digital.insinyur.irame.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.UsersResponse
import co.id.digital.insinyur.irame.ui.users.adapter.AdapterPagedUsers
import co.id.digital.insinyur.irame.ui.users.adapter.AdapterStateUsers
import co.id.digital.insinyur.irame.util.Coroutines
import co.id.digital.insinyur.irame.util.UsersOptionListener
import co.id.digital.insinyur.irame.util.visibleWhen
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest


class UListFragment : Fragment(), UsersOptionListener {

    private lateinit var viewModel: UsersViewModel

    private lateinit var tvDescription: TextView

    private lateinit var btnAdd: Button

    private lateinit var btnRefresh: Button

    private lateinit var pbLoading: ProgressBar

    private lateinit var rvContent: RecyclerView

    private val adapterPaged: AdapterPagedUsers by lazy { AdapterPagedUsers(this) }

    private var fetchUsersJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {

        viewModel = ViewModelProvider(requireActivity()).get(UsersViewModel::class.java)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_u_list, group, false)

        tvDescription = view.findViewById(R.id.tv_description)
        btnAdd = view.findViewById(R.id.btn_add)
        btnRefresh = view.findViewById(R.id.btn_refresh)
        pbLoading = view.findViewById(R.id.pb_loading)

        rvContent = view.findViewById(R.id.rv_content)

        initAdapter()


//        rvContent.layoutManager = LinearLayoutManager(activity)
//        rvContent.setHasFixedSize(true)
//
//        adapter = AdapterListUsers(rowsArrayList, this)
//        rvContent.adapter = adapter
//
//        rvContent.addOnScrollListener(object: RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
//                if (!isLoading) {
//                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() < (rowsArrayList.size - 1)) {
//                        //bottom of list!
//                        showLoading()
//                        viewModel.fetch(rowsArrayList[rowsArrayList.size - 1])
//                    }
//                }
//            }
//        })

//        viewModel.result.observe(requireActivity(), androidx.lifecycle.Observer {
//            if (it != null){
//                if (rowsArrayList.size > 0) hideLoading()
//
//                if (rowsArrayList.size >= it.totalPage) return@Observer
//
//                rowsArrayList.addAll(it.users)
//                adapter.notifyDataSetChanged()
//
//                isLoading = false
//            }
//        })
//
//        viewModel.error.observe(requireActivity(), androidx.lifecycle.Observer {
//            if (it != null){
//                showMessage(it)
//            }
//        })


//        viewModel.fetch(null)

        fetchUsers()

        btnRefresh.setOnClickListener {
            adapterPaged.retry()
        }

        btnAdd.setOnClickListener {
            viewModel.setSelected(UsersResponse(id = 0, username = "", packageName = ""))
        }
        return view
    }

    private fun initAdapter(){

        rvContent.layoutManager = LinearLayoutManager(activity)
        rvContent.setHasFixedSize(true)

        val dividerItemDecoration = DividerItemDecoration(
            requireContext(), DividerItemDecoration.VERTICAL
        )
        rvContent.addItemDecoration(dividerItemDecoration)
        rvContent.adapter = adapterPaged.withLoadStateHeaderAndFooter(
            header = AdapterStateUsers { adapterPaged.retry() },
            footer = AdapterStateUsers { adapterPaged.retry()}
        )

        adapterPaged.addLoadStateListener { loadState ->

            loadState.refresh.let {
                tvDescription.visibleWhen(it is LoadState.Error || adapterPaged.itemCount == 0)
                btnAdd.visibleWhen((it !is LoadState.Error) && adapterPaged.itemCount == 0)
                btnRefresh.visibleWhen(it is LoadState.Error)
                pbLoading.visibleWhen(it is LoadState.Loading)
                rvContent.visibleWhen(it is LoadState.NotLoading)

                if (it is LoadState.Error){
                    tvDescription.text = it.error.localizedMessage
                } else if ((it !is LoadState.Error) && adapterPaged.itemCount == 0){
                    tvDescription.text = "no item found..."
                }
            }
        }
    }

    private fun fetchUsers() {
        fetchUsersJob?.cancel()
        fetchUsersJob = Coroutines.main {
            viewModel.usersList().collectLatest {
                adapterPaged.submitData(it)
            }
        }
    }

    override fun onOptionItemClick(id: Int, users: UsersResponse) {
        when(id){
            R.id.menu_create, R.id.menu_update -> { viewModel.setSelected(users) }
            R.id.menu_delete -> { }
        }
    }
}
