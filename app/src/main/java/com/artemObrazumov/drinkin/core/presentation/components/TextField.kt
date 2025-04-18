package com.artemObrazumov.drinkin.core.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artemObrazumov.drinkin.app.ui.theme.blendTextColor
import com.artemObrazumov.drinkin.app.ui.theme.darkTextColor

@Composable
fun TextField(
    value: String,
    onValueChanged: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    placeHolder: String = "",
    singleLine: Boolean = true,
    afterButton: (@Composable () -> Unit)? = null,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val borderColorState by animateColorAsState(
        if (isFocused) borderColor else blendTextColor
    )

    Box (
        modifier = modifier
            .border(
                width = 2.dp,
                color = borderColorState,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        if (value.isEmpty()) {
            Text(
                text = placeHolder,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = blendTextColor
                )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            BasicTextField(
                modifier = Modifier
                    .weight(1f),
                value = value,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkTextColor
                ),
                singleLine = singleLine,
                onValueChange = onValueChanged,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource
            )
            afterButton?.invoke()
        }
    }
}