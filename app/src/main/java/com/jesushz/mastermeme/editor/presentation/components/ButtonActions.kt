package com.jesushz.mastermeme.editor.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jesushz.mastermeme.R

@Composable
fun ButtonActions(
    modifier: Modifier = Modifier,
    onAddTextClick: () -> Unit,
    onSaveMemeClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = onAddTextClick,
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = stringResource(R.string.add_text),
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = onSaveMemeClick,
                shape = MaterialTheme.shapes.small,
            ) {
                Text(
                    text = stringResource(R.string.save_meme),
                )
            }
        }
    }
}
