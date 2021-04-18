package co.id.digital.insinyur.irame.ui.users.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import co.id.digital.insinyur.irame.databinding.ItemLoadStateBinding
import co.id.digital.insinyur.irame.util.visibleWhen

class AdapterStateUsers(private val retry: () -> Unit) : LoadStateAdapter<AdapterStateUsers.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ItemLoadStateBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LoadStateViewHolder(binding, retry)
    }

    inner class LoadStateViewHolder(val view: ItemLoadStateBinding, retry: () -> Unit): RecyclerView.ViewHolder(view.root){

        init { view.stateRetryButton.setOnClickListener { retry.invoke() } }

        fun bind(loadState: LoadState){
            itemView.apply {
                view.stateProgressBar.visibleWhen(loadState is LoadState.Loading)
                view.stateErrorMsgTextView.visibleWhen(loadState is LoadState.Error)
//                state_progressBar.visibleWhen(loadState is LoadState.Loading)
//                state_error_msg_textView.visibleWhen(loadState is LoadState.Error)
                view.stateRetryButton.visibleWhen(loadState is LoadState.Error)
            }

            if (loadState is LoadState.Error){
                view.stateErrorMsgTextView.text = loadState.error.localizedMessage
            }
//            if (loadState is LoadState.Error){
//                view.stateErrorMsgTextView.visibility = View.VISIBLE
//                view.stateRetryButton.visibility = View.VISIBLE
//                view.stateProgressBar.visibility = View.GONE
//                view.stateErrorMsgTextView.text = loadState.error.localizedMessage
//            } else if (loadState is LoadState.Loading){
//                view.stateProgressBar.visibility = View.VISIBLE
//                view.stateErrorMsgTextView.visibility = View.GONE
//                view.stateRetryButton.visibility = View.GONE
//            }
        }
    }

}