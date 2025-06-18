package com.london.tudee.domain.services

import kotlinx.coroutines.flow.Flow

interface Services<T> {
    suspend fun add(service: T) : Boolean
    suspend fun edit(service: T) : Boolean
    suspend fun delete(service: T) : Boolean
    suspend fun getAll(): Flow<List<T>>
    suspend fun getById(id: Int) : T
}