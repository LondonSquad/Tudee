package com.london.tudee.domain.services

import com.london.tudee.domain.entities.Category

interface CategoryService : Services<Category>{
    fun getIconResById(id: Int): Int
}