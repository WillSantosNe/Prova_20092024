package com.example.prova_20092024

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// Função para exibir cada detalhe do produto com estilo personalizado
@Composable
fun DetalheProduto(label: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 18.sp, // Tamanho da fonte
            fontWeight = FontWeight.Medium, // Peso da fonte
            color = Color.Black // Cor do rótulo
        )
        Text(
            text = valor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray // Cor do valor
        )
    }
}




// Maneira de como o produto será exibido na Listagem
@Composable
fun ProdutoItem(produto: Produto, onDetalhesClick: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${produto.nome} (${produto.quantidade_est} unidades)")

            Button(
                onClick = onDetalhesClick
            ) {
                Text(text = "Detalhes")
            }
        }
    }
}