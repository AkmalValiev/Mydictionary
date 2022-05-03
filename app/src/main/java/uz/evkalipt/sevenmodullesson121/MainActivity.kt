package uz.evkalipt.sevenmodullesson121

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import uz.evkalipt.sevenmodullesson121.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBarMain.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(binding.navView)
        }

        var navController = findNavController(R.id.fragment)
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    navController.navigateUp()
                    navController.navigate(R.id.firstFragment)
                }
                R.id.search -> {
                    navController.navigateUp()
                    navController.navigate(R.id.searchFragment)
                }
                R.id.save -> {
                    navController.navigateUp()
                    navController.navigate(R.id.saveFragment)
                }
                R.id.time -> {
                    navController.navigateUp()
                    navController.navigate(R.id.seenFragment)
                }
            }
            true
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.fragment).navigateUp()
    }

    override fun onBackPressed() {

        if (binding.drawerLayout.isOpen) {
            binding.drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }


    }
}