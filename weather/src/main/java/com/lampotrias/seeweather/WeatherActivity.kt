package com.lampotrias.seeweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.lampotrias.seeweather.databinding.ActivityMainBinding
import com.lampotrias.seeweather.presentation.mainscreen.MainScreenFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	private lateinit var navController: NavController
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		navController = findNavController(R.id.nav_host_fragment)
		binding.bottomNavigation.setupWithNavController(navController)

		binding.bottomNavigation.setOnItemSelectedListener { item ->
			when (item.itemId) {
				R.id.current_weather -> {
					navController.navigate(R.id.nav_mainScreenFragment)
					return@setOnItemSelectedListener true
				}
				R.id.add_location -> {
					navController.navigate(R.id.nav_cityFragment)
					return@setOnItemSelectedListener true
				}
				R.id.location_list -> {
					navController.navigate(R.id.nav_cityListFragment)
					return@setOnItemSelectedListener true
				}
				R.id.settings -> {
					navController.navigate(R.id.nav_settingsFragment)
					return@setOnItemSelectedListener true
				}
				else -> {
					return@setOnItemSelectedListener false
				}
			}
		}

		binding.bottomNavigation.setOnItemReselectedListener { item ->
			when (item.itemId) {
				R.id.current_weather -> {
					supportFragmentManager.setFragmentResult(
						MainScreenFragment.RESULT_KEY_RESELECT_TAB, bundleOf()
					)
				}
			}
		}

		supportFragmentManager.setFragmentResultListener(
			MainScreenFragment.RESULT_KEY_FROM_CITY_LIST,
			this
		) { requestKey, _ ->
			if (requestKey == MainScreenFragment.RESULT_KEY_FROM_CITY_LIST) {
				binding.bottomNavigation.selectedItemId = R.id.current_weather
			}
		}
	}

	override fun onBackPressed() {
//		navController.previousBackStackEntry?.let {
//			val destinationFragment = it.destination.id
//			if (childFragmentList.contains(destinationFragment)){
//				navView.menu[childFragmentMenuList[childFragmentList.indexOf(destinationFragment)]].isChecked = true
//			}
//		}
//
//		super.onBackPressed()
	}
}