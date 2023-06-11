package com.example.personalcoach.presenter.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.personalcoach.presenter.ui.theme.ExtendedJetStyle
import com.example.personalcoach.presenter.ui.theme.ExtendedJetTheme
import com.example.personalcoach.presenter.ui.theme.*


import com.example.personalcoach.presenter.ui.theme.MainTheme


data class CardModel(
    val title: String,
    val isChecked: Boolean
)


//@ExperimentalMaterial3Api
@Composable
fun CardItem(
    model: CardModel,
    onCheckedChange: ((Boolean) -> Unit)?=null
){

    Card(
        modifier = Modifier
            .padding(
                horizontal = ExtendedJetTheme.shapes.padding,
                vertical = ExtendedJetTheme.shapes.padding / 2
            )
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = ExtendedJetTheme.colors.primaryBackground
        ),
        shape = ExtendedJetTheme.shapes.cornersStyle,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation =8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(ExtendedJetTheme.shapes.padding)
                .fillMaxWidth()
        ){
            Text(
                modifier = Modifier.weight(1f),
                text = model.title,
                style = ExtendedJetTheme.typography.body,
                color = ExtendedJetTheme.colors.primaryText
            )
            Checkbox(checked = model.isChecked,
                onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = ExtendedJetTheme.colors.tintColor,
                uncheckedColor = ExtendedJetTheme.colors.secondaryText
            ))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PurpleLightItemPreview(){
    ThemeJetCard(isDarkMode = false, style = ExtendedJetStyle.Purple)
}

@Composable
@Preview(showBackground = true)
fun PurpleDarkItemPreview(){
    ThemeJetCard(isDarkMode = true, style = ExtendedJetStyle.Purple)
}

@Composable
@Preview(showBackground = true)
fun BlueLightItemPreview(){
    ThemeJetCard(isDarkMode = false, style = ExtendedJetStyle.Blue)
}


@Composable
@Preview(showBackground = true)
fun BlueDarkItemPreview(){
    ThemeJetCard(isDarkMode = true, style = ExtendedJetStyle.Blue)
}

@Composable
private fun ThemeJetCard(
    isDarkMode: Boolean,
    style: ExtendedJetStyle
){
    MainTheme(darkTheme = isDarkMode, style = style) {
        Surface(color = ExtendedJetTheme.colors.primaryBackground) {
            CardItem(model = CardModel("Чистить зубы", isChecked = true))
        }
    }
}