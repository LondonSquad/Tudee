import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun StepIndicatorBar(
    activeStep: Int, totalSteps: Int = 3, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        repeat(totalSteps) { index ->
            val animatedColor by animateColorAsState(
                targetValue = if (index == activeStep) TudeeTheme.colors.primary
                else TudeeTheme.colors.primaryVariant, label = "stepColorAnimation"
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(5.dp)
                    .clip(TudeeTheme.shapes.circle)
                    .background(animatedColor)
            )

            if (index < totalSteps - 1) {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}
