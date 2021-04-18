package co.id.digital.insinyur.irame.ui.menu

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.MenuResponse
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MenuActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: MenuViewModelFactory by instance()

    private lateinit var viewModel: MenuViewModel

    private lateinit var listFragment: MListFragment

    private lateinit var  formFragment: MFormFragment

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        viewModel = ViewModelProvider(this, factory).get(MenuViewModel::class.java)

        viewModel.message.observe(this, Observer {
            if (it != null){
                showMessage(it)
            }
        })
        listFragment = MListFragment()
        formFragment = MFormFragment()

        progressBar = findViewById(R.id.pb_loading)

        viewModel.loading.observe(this, Observer {
            if (it == true) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
        })

        viewModel.selectedMenu.observe(this, Observer {
            if (it == null) displayListFragment() else displayFormFragment()
        })

        displayListFragment()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.item_add -> {
                val tz = TimeZone.getTimeZone("GMT+8")
                val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                df.timeZone = tz

                val menuResponse = MenuResponse(
                    id = 0,
                    idPackage = 0,
                    name = "",
                    profile = "",
                    createdAt = df.format(Date())
                )
                viewModel.selectedMenu(menuResponse)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayListFragment() {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (listFragment.isAdded) { // if the fragment is already in container
            ft.show(listFragment)
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fl_context, listFragment, "listFragment")
        }
        // Hide fragment B
        if (formFragment.isAdded) {
            ft.hide(formFragment)
        }
        // Commit changes
        ft.commit()
    }

    private fun displayFormFragment() {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (formFragment.isAdded) { // if the fragment is already in container
            ft.show(formFragment)
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fl_context, formFragment, "formFragment")
        }
        // Hide fragment B
        if (listFragment.isAdded) {
            ft.hide(listFragment)
        }
        // Commit changes
        ft.commit()
    }
}