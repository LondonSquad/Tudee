package com.london.tudee.data.local.room_db

import com.london.tudee.R
import com.london.tudee.data.local.room_db.entities.CategoryDto


val defaultCategory = listOf(
    CategoryDto(
        id = 0, name = "Education",
        iconPath = R.drawable.ic_education.toString(),
        arName = "التعليم",
        isDefault = true,
    ), CategoryDto(
        id = 0,
        name = "Shopping",
        arName = "التسوق",
        isDefault = false,
        iconPath = R.drawable.ic_shopping.toString()
    ), CategoryDto(
        id = 0,
        name = "Medical",
        arName = "الطب",
        isDefault = false,
        iconPath = R.drawable.ic_medical.toString()
    ), CategoryDto(
        id = 0, name = "Gym",
        iconPath = R.drawable.ic_gym.toString(),
        arName = "التمرين",
        isDefault = false,
    ), CategoryDto(
        id = 0,
        name = "Entertainment",
        arName = "الترفيه",
        isDefault = false,
        iconPath = R.drawable.ic_entertainment.toString()
    ), CategoryDto(
        id = 0,
        name = "Cooking",
        arName = "الطب",
        isDefault = false,
        iconPath = R.drawable.ic_cooking.toString()
    ), CategoryDto(
        id = 0,
        name = "Family & friend",
        arName = "الأسرة والاصدقاء",
        isDefault = false,
        iconPath = R.drawable.ic_family.toString()
    ), CategoryDto(
        id = 0,
        name = "Traveling",
        arName = "السفر",
        isDefault = false,
        iconPath = R.drawable.ic_travel.toString()
    ), CategoryDto(
        id = 0,
        name = "Agriculture",
        arName = "الزراعة",
        isDefault = false,
        iconPath = R.drawable.ic_agriculture.toString()
    ), CategoryDto(
        id = 0,
        name = "Coding",
        arName = "البرمجة",
        isDefault = false,
        iconPath = R.drawable.ic_coding.toString()
    ), CategoryDto(
        id = 0,
        name = "Adoration",
        arName = "الاعزاء",
        isDefault = false,
        iconPath = R.drawable.ic_adoration.toString()
    ), CategoryDto(
        id = 0,
        name = "Fixing bugs",
        arName = "التصليح",
        isDefault = false,
        iconPath = R.drawable.ic_bug_fix.toString()
    ), CategoryDto(
        id = 0,
        name = "Cleaning",
        arName = "التنظيف",
        isDefault = false,
        iconPath = R.drawable.ic_cleaning.toString()
    ), CategoryDto(
        id = 0,
        name = "Work",
        arName = "العمل",
        isDefault = false,
        iconPath = R.drawable.ic_work.toString()
    ), CategoryDto(
        id = 0,
        name = "Budgeting",
        arName = "الحسابات",
        isDefault = false,
        iconPath = R.drawable.ic_budgeting.toString()
    )
)
