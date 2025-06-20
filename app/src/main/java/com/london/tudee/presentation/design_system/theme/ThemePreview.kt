package com.london.tudee.presentation.design_system.theme

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "LIGHT",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    locale = "en"
)
@Preview(
    name = "DARK",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    locale = "en"
)
@Preview(
    name = "Arabic - RTL",
    showBackground = true,
    locale = "ar",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
annotation class ThemePreviews
