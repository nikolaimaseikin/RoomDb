package com.example.roomdb

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    val mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory)
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        val itemsList = mainViewModel.itemsList.collectAsState(initial = emptyList())
        Column(modifier = Modifier.fillMaxSize()){
            Row {
                TextField(
                    value = mainViewModel.textFieldData.value,
                    onValueChange = { mainViewModel.textFieldData.value = it },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                    label = {
                        Text(text = "Name")
                    }
                )
                IconButton(
                    onClick = { mainViewModel.insertItem() }
                ) {
                   Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            LazyColumn(modifier = Modifier.fillMaxWidth()){
                items(itemsList.value){item ->
                    ListItem(item, {
                        mainViewModel.nameEntity = it
                        mainViewModel.textFieldData.value = it.name },
                        {mainViewModel.deleteItem(it)}
                    )
                }
            }
        }
    }
}