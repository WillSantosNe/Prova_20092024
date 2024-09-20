package com.example.prova_20092024

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TelaCadastroProduto()
        }
    }
}

@Composable
fun TelaCadastroProduto() {
    var nome by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var quantidadeEstoque by remember { mutableStateOf("") }
    var mensagemSucesso by remember { mutableStateOf(false) } // Estado para controlar a exibição da mensagem de sucesso

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Cabeçalho centralizado
        Text(
            text = "CADASTRO DE PRODUTOS",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo Nome do Produto
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = nome,
            onValueChange = {
                nome = it
                mensagemSucesso = false // Oculta mensagem ao editar campo
            },
            label = { Text(text = "Nome do Produto") }
        )

        // Campo Categoria do Produto
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = categoria,
            onValueChange = {
                categoria = it
                mensagemSucesso = false // Oculta mensagem ao editar campo
            },
            label = { Text(text = "Categoria") }
        )

        // Campo Preço do Produto
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = preco,
            onValueChange = {
                preco = it
                mensagemSucesso = false // Oculta mensagem ao editar campo
            },
            label = { Text(text = "Preço") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Campo Quantidade em Estoque
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = quantidadeEstoque,
            onValueChange = {
                quantidadeEstoque = it
                mensagemSucesso = false // Oculta mensagem ao editar campo
            },
            label = { Text(text = "Quantidade em Estoque") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botão de Cadastrar
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .height(50.dp),
            onClick = {
                val precoDouble = preco.toDoubleOrNull()
                val quantidadeInt = quantidadeEstoque.toIntOrNull()

                when {
                    nome.isBlank() || categoria.isBlank() || preco.isBlank() || quantidadeEstoque.isBlank() -> {
                        Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    }
                    precoDouble == null -> {
                        Toast.makeText(context, "Preço inválido", Toast.LENGTH_SHORT).show()
                    }
                    quantidadeInt == null -> {
                        Toast.makeText(context, "Quantidade inválida", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        ListaProdutos.listaProdutos.add(Produto(nome, categoria, precoDouble, quantidadeInt))
                        nome = ""
                        categoria = ""
                        preco = ""
                        quantidadeEstoque = ""
                        mensagemSucesso = true
                    }
                }
            }
        ) {
            Text(text = "Cadastrar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mensagem de Produto Cadastrado
        if (mensagemSucesso) {
            Text(
                text = "Produto cadastrado com sucesso!",
                color = Color.Green,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Composable
fun Main(){

}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    TelaCadastroProduto()
}