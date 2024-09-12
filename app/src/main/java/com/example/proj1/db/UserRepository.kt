package com.example.proj1.db

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(private val userDao: UserDao) {
    val readAlldata: LiveData<List<User>> = userDao.readAlldata()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addUser(user: User) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.insert(user)
        }
    }
}