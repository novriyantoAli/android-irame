package co.id.digital.insinyur.irame.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.databinding.ActivityMainBinding
import co.id.digital.insinyur.irame.ui.authenticate.AuthenticateActivity
import co.id.digital.insinyur.irame.ui.menu.MenuActivity
import co.id.digital.insinyur.irame.util.BottomNavigationBehavior
import co.id.digital.insinyur.irame.util.DarkModePrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,  KodeinAware {

    override val kodein by kodein()

    private val factory: MainViewModelFactory by instance()

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        viewModel.resultLogout.observe(this, Observer {
            if (it == true) moveToAuthenticate() else showMessage("failed to logout...")
        })
        viewModel.menu.observe(this, Observer {
            if (it == true) moveToMenu() else showMessage("failed to open menu")
        })
        viewModel.activity.observe(this, Observer {
            if (it != null){ moveToActivity(it) }
        })
        viewModel.nas.observe(this, Observer {
            if (it.id == null){ showForm() }
        })
        viewModel.message.observe(this, Observer {
            if (it != null){ showMessage(it) }
        })

        if (DarkModePrefManager(this).isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view: View = binding.root

        setContentView(view)

        setSupportActionBar(binding.appBarMain.toolbar)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.appBarMain.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this@MainActivity)

        //
//        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//
//        val navController = findNavController(R.id.nav_host_fragment)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.navigation_radius, R.id.navigation_finance, R.id.navigation_setting)
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        setupNavigation()

        viewModel.checkNAS()
    }

    override fun onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setupNavigation() {
        val layoutParams = binding.appBarMain.bottomNavigationView.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationBehavior()
        navHostFragment = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)!!
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
    }

    private fun moveToActivity(it: Class<*>) {
        val intent = Intent(this, it)
        startActivity(intent)
    }

    private fun moveToMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showForm(){
        val builder = AlertDialog.Builder(this@MainActivity)

        val root = layoutInflater.inflate(R.layout.dialog_nas, null)

        val host = root.findViewById<EditText>(R.id.et_host)
        val port = root.findViewById<EditText>(R.id.et_port)

        builder.setView(root)
        builder.setTitle(R.string.title_nas)
            // Add action buttons
            .setPositiveButton(R.string.title_save) { _, _ ->
                val hostString = host.text.toString()
                val portString = port.text.toString()

                if (hostString.trim().isEmpty() || portString.trim().isEmpty()){
                    showMessage("field cannot be empty...")
                    viewModel.defaultNAS()
                } else {
                    viewModel.saveNAS(hostString, portString)
                }
            }
        builder.setCancelable(false)
        builder.create()

        builder.show()
    }

    private fun moveToAuthenticate() {
        val intent = Intent(this, AuthenticateActivity::class.java)
        startActivity(intent)

        finish()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        // Handle navigation view item clicks here.
        val id = item.itemId

        when (id) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {
            }
            R.id.nav_slideshow -> {
            }
            R.id.nav_manage -> {
            }
            R.id.nav_share -> {
            }
            R.id.nav_dark_mode -> {
                //code for setting dark mode
                //true for dark mode, false for day mode, currently toggling on each click
                val darkModePrefManager = DarkModePrefManager(this)
                darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                recreate()
            }

            //        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

            //        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}