package com.london.tudee.presentation.mapper

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.london.tudee.domain.entities.Category

object CategoryMapper {

    fun getIconResource(category: Category): Int {
        // Try to parse the iconPath as an integer (resource ID)
        return category.iconRes.toIntOrNull() ?: -1
    }

//    @SuppressLint("LocalContextConfigurationRead")
//    @Composable
//    fun getCategoryDisplayName(category: Category): String {
//        val context = LocalContext.current
//        val currentLocale = context.resources.configuration.locales[0]
//        return when (currentLocale.language) {
//            "ar" -> category.arName
//            else -> category.name
//        }
//    }
}