package edu.android_security.interview.lifecycle.counterApp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import edu.android_security.ui.theme.Android_securityTheme

class CounterViewModel : ViewModel() {
    var vmCounted = MutableLiveData(1)
    var lifeCycleState = MutableLiveData<List<String>>(emptyList())
    fun setState(state: String) {
        lifeCycleState.value = lifeCycleState.value?.plus(state) ?: listOf(state)
    }

    fun clearState() {
        lifeCycleState.value = emptyList()
    }

    override fun onCleared() {
        super.onCleared()
    }
}

class CounterAppActivity : ComponentActivity() {
    val viewModel by viewModels<CounterViewModel>()

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setState("onCreate")

        setContent {
            val lifeCycleState = remember { mutableStateOf(emptyList<String>()) }
            DisposableEffect(key1 = viewModel.lifeCycleState)
            {
                val observer = Observer<List<String>> {
                    lifeCycleState.value = it
                }
                viewModel.lifeCycleState.observeForever(observer)
                onDispose {
                    viewModel.lifeCycleState.removeObserver(observer)
                }

            }

            Android_securityTheme {
                Column(Modifier.padding(12.dp))
                {

                    CounterParent(vmCounted = viewModel)
                    LifeCycleState(state = lifeCycleState.value){viewModel.clearState()}
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.setState("onStart")
    }

    // set life Cycle state
    override fun onResume() {
        super.onResume()
        viewModel.setState("onResume")
    }

    override fun onPause() {
        super.onPause()
        viewModel.setState("onPause")
    }

    override fun onStop() {
        super.onStop()
        viewModel.setState("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.setState("onDestroy")

    }
}


@Composable
fun CounterParent(vmCounted: CounterViewModel) {
    val cnt = rememberSaveable { mutableIntStateOf(1) }
    var showCounter by remember { mutableStateOf(true) } // Controls whether the Counter Composable is shown


    Column {
        if (showCounter) {
            Counter(
                rememberSavableFromOutSide = cnt.value,
                viewmodelCounted = vmCounted.vmCounted.value
            ) {
                cnt.value++
                vmCounted.vmCounted.value = vmCounted.vmCounted.value?.let { it + 1 } ?: 1

            }

        }
        Button(onClick = { showCounter = !showCounter })
        {
            Text("Toggle Counter Visibility")
        }
    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun Counter(rememberSavableFromOutSide: Int, viewmodelCounted: Int?, onClick: () -> Unit) {
    var cnt2 = remember { mutableIntStateOf(1) }
    var cnt4 = rememberSaveable { mutableIntStateOf(1) }
    var cnt3 = mutableIntStateOf(1)  // No remember

    Column(modifier = Modifier.padding(12.dp)) {
        Text(text = "Example For Compose State ")
        Text(text = "$rememberSavableFromOutSide remember Savable from outside scope")
        Text(text = "$viewmodelCounted viewModelCounted")
        Text(text = "${cnt4.value} remember Savable inside composable scope")
        Text(text = "${cnt2.value} remember only")
        Text(text = "${cnt3.value} no remember")
        Button(onClick = {
            cnt2.value++
            cnt3.value++
            cnt4.value++
            onClick.invoke()
        }, content = { Text("Click me") })
    }
}

@Composable
fun LifeCycleState(state: List<String>,onClear: () -> Unit = {}) {

    Text(text = "${state.toString()}")
    Button(onClick = onClear) {
        Text(text = "Clear Life cycle State")
    }
}