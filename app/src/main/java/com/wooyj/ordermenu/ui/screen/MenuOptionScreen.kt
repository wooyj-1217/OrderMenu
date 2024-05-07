package com.wooyj.ordermenu.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.TempOption
import com.wooyj.ordermenu.data.caffeinOptionList
import com.wooyj.ordermenu.data.iceOptionList
import com.wooyj.ordermenu.data.tempOptionList
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme
import com.wooyj.ordermenu.utils.addCommasToNumber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuOptionScreen(navController: NavController, menu: MenuType) {
    OrderMenuTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = {}, navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
            },
            content =
            {
                MenuOptionUI(Modifier.padding(it), navController, menu)
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewMenuOptionScreen() {
    OrderMenuTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = {}, navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
            },
            content =
            {
                MenuOptionUI(
                    Modifier.padding(it),
                    null,
                    MenuType.Beverage("망고에이드", 2500, listOf(TempOption.Ice))
                )
            })
    }
}


@Composable
fun MenuOptionUI(modifier: Modifier, navController: NavController?, menu: MenuType) {

    var optionTemp by remember {
        mutableStateOf(
            if (menu is MenuType.Beverage) TempOption.Ice.name.uppercase() else TempOption.Hot.name.uppercase()
        )
    }
    var optionCaffein by remember { mutableStateOf("카페인") }
    var optionIce by remember { mutableStateOf("적게") }

    val onTempChanged = { text: String -> optionTemp = text }
    val onCaffeinChanged = { text: String -> optionCaffein = text }
    val onIceChanged = { text: String -> optionIce = text }


    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
        Column(modifier = modifier
            .padding(start = 20.dp, top = 60.dp, end = 20.dp)
            .weight(1f)) {
            Text(menu.menuName)
            Text("${menu.price.addCommasToNumber()}원")
            Spacer(modifier = Modifier.height(40.dp))

            when (menu) {
                is MenuType.Coffee -> {
                    MenuOption(
                        tempOptionList, "기본", optionTemp, onTempChanged
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    MenuOption(
                        caffeinOptionList, "디카페인", optionCaffein, onCaffeinChanged
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    if (optionTemp == TempOption.Ice.name.uppercase()) {
                        MenuOption(
                            iceOptionList, "얼음", optionIce, onIceChanged
                        )
                    }
                }

                is MenuType.Beverage -> {
                    optionCaffein = ""

                    MenuOption(
                        listOf(TempOption.Ice.name.uppercase()), "기본", optionTemp, onTempChanged
                    )
                    if (optionTemp == TempOption.Ice.name.uppercase()) {
                        MenuOption(
                            iceOptionList, "얼음", optionIce, onIceChanged
                        )
                    }
                }

                is MenuType.Tea -> {
                    optionTemp = ""
                    optionIce = ""

                    MenuOption(
                        caffeinOptionList, "디카페인", optionCaffein, onCaffeinChanged
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

                is MenuType.Dessert -> {
                    optionTemp = ""
                    optionCaffein = ""
                    optionIce = ""
                }
            }
        }

        Button(modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(16.dp), onClick = {
            val options = mutableListOf<String>()
            if (optionTemp != "") {
                options.add(optionTemp)
            }
            if (optionCaffein != "") {
                options.add(optionCaffein)
            }
            if (optionIce != "") {
                options.add("얼음($optionIce)")
            }
            options.joinToString(",")
            navController?.navigate(
                "menuConfirm/result=${options}&menuName=${menu.menuName}&price=${menu.price}"
            )
        }
        ) {
            Text("다음", fontSize = 20.sp)
        }
    }
}

@Composable
fun MenuOption(
    options: List<String>,
    title: String,
    option: String,
    onChanged: (String) -> Unit
) {
    Column {
        Text(title)
        ToggleButtonGroup(options, option, onChanged)
    }
}

@Composable
fun ToggleButtonGroup(optionList: List<String>, option: String, onChanged: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        optionList.forEach {
            Button(
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
                    .padding(4.dp),
                onClick = {
                    println("onClick >>> $it")
                    onChanged(it)
                    println("$option")
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = if (it == option) Color.DarkGray else Color.LightGray
                )
            ) {
                Text(it)
            }
        }
    }
}