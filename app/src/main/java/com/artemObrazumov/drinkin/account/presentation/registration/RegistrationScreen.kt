package com.artemObrazumov.drinkin.account.presentation.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.R

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 96.dp)
    ) {
        Text(
            text = stringResource(R.string.registration_title),
            style = TextStyle()
        )
    }
}