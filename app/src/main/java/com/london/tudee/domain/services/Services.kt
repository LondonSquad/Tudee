package com.london.tudee.domain.services

import kotlinx.coroutines.flow.Flow

interface Services<T> {
    suspend fun add(service: T)
    suspend fun edit(service: T)
    suspend fun delete(service: T)
    suspend fun getAll(): Flow<List<T>>
    suspend fun getById(id: Int) : T
}