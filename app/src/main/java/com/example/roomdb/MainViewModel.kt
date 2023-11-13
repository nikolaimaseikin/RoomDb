package com.example.roomdb

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.roomdb.data.MainDb
import com.example.roomdb.data.NameEntity
import kotlinx.coroutines.launch

class MainViewModel(val database: MainDb): ViewModel() {
    val itemsList = database.dao.getAllItems()
    val textFieldData = mutableStateOf("")
    var nameEntity: NameEntity? = null

    fun insertItem() = viewModelScope.launch {
        val nameItem = nameEntity?.copy(name = textFieldData.value) ?: NameEntity(name = textFieldData.value)
        database.dao.insertItem(nameItem)
        nameEntity = null
        textFieldData.value = ""
    }

    fun deleteItem(item: NameEntity) = viewModelScope.launch {
        database.dao.deleteItem(item)
    }

    companion object{
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val database = checkNotNull(extras[APPLICATION_KEY] as App).database
                return MainViewModel(database) as T
            }
        }
    }
}