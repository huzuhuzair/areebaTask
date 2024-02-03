package com.aamir.employeemanagement

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.aamir.employeemanagement.networkUtils.EmployeeApi
import com.aamir.employeemanagement.di.EmployeeViewModelFactory
import com.aamir.employeemanagement.di.addEmployeeScreen
import com.aamir.employeemanagement.di.employeeListScreen
import com.aamir.employeemanagement.viewModels.EmployeeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var api: EmployeeApi

    private val viewModel: EmployeeViewModel by viewModels {
        EmployeeViewModelFactory(api)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(viewModel)
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp(viewModel: EmployeeViewModel) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "employeeListScreen") {
        addEmployeeScreen(viewModel,navController)
        employeeListScreen(viewModel,navController)
    }
}



@Composable
fun <T> LiveData<T>.observeInComposition(onChange: (T) -> Unit) {
    val context = LocalContext.current

    DisposableEffect(context) {
        val observer = Observer<T> { value ->
            onChange(value)
        }

        observe(context as LifecycleOwner, observer)

        onDispose {
            removeObserver(observer)
        }
    }
}

