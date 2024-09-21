package com.example.prova_20092024

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Main()
        }
    }
}


@Composable
fun TelaCadastroProduto(navController: NavController) {
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
    ) {
        Text(
            text = "CADASTRO DE PRODUTOS",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            color = Color.DarkGray
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Corrige para aceitar números decimais
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

        Spacer(modifier = Modifier.height(16.dp)) // Espaçamento entre o formulário e a mensagem

        // Box para manter o espaço da mensagem
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp) // Altura reservada para a mensagem
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center // Centraliza o texto dentro do Box
        ) {
            if (mensagemSucesso) {
                Text(
                    text = "Produto cadastrado com sucesso!",
                    color = Color.Green,
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espaço adicional para separar o botão

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
                    precoDouble == null || precoDouble < 0 -> {
                        Toast.makeText(context, "Preço inválido. Deve ser maior ou igual a zero.", Toast.LENGTH_SHORT).show()
                    }
                    quantidadeInt == null || quantidadeInt < 1 -> {
                        Toast.makeText(context, "Quantidade inválida. Deve ser maior ou igual a 1.", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        val produto = Produto(nome, categoria, precoDouble, quantidadeInt)
                        val sucesso = Estoque.adicionarProduto(produto) // Usando a classe Estoque para adicionar o produto
                        if (sucesso) {
                            nome = ""
                            categoria = ""
                            preco = ""
                            quantidadeEstoque = ""
                            mensagemSucesso = true
                        } else {
                            Toast.makeText(context, "Erro ao adicionar produto. Verifique os dados.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        ) {
            Text(text = "Cadastrar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão para ir à tela de listagem de produtos
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate("telaLista") }
        ) {
            Text(text = "Ir para Listagem de Produtos")
        }
    }
}


@Composable
fun TelaListaProdutos(navController: NavController) {
    // Recuperando lista de produtos de outra tela
    val produtos = remember { Estoque.listaProdutos }

    // Inicializando o Gson
    val gson = remember { Gson() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "LISTA DE PRODUTOS",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            color = Color.DarkGray
        )

        if (produtos.isEmpty()) {

            // Mensagem de lista vazia
            Text(
                text = "Nenhum produto cadastrado.",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // Usando weight para que a LazyColumn não ocupe 100% do espaço e empurre o botão
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // A LazyColumn vai ocupar apenas uma parte do espaço disponível
            ) {
                items(produtos) { produto ->
                    ProdutoItem(produto = produto, onDetalhesClick = {

                        // Serializa o objeto Produto para JSON usando Gson
                        val produtoJson = gson.toJson(produto)

                        navController.navigate("telaDetalhes/$produtoJson")
                    })
                }
            }
        }

        // Botão para exibir as estatísticas
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate("telaEstatisticas") }
        ) {
            Text(text = "Estatísticas")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão para voltar à tela de cadastro de produtos
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.popBackStack() }
        ) {
            Text(text = "Voltar ao Cadastro de Produtos")
        }
    }
}


@Composable
fun TelaDetalhesProduto(navController: NavController, produtoJson: String?) {

    // Capturando objeto JSON
    val gson = remember { Gson() }

    // Desserializa o JSON de volta para o objeto Produto
    val produto: Produto = gson.fromJson(produtoJson, object : TypeToken<Produto>() {}.type)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Detalhes do Produto",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Informações do produto com estilo personalizado
        DetalheProduto(label = "Nome:", valor = produto.nome)
        DetalheProduto(label = "Categoria:", valor = produto.categoria)
        DetalheProduto(label = "Preço:", valor = "R$ ${produto.preco}")
        DetalheProduto(label = "Quantidade em Estoque:", valor = "${produto.quantidade_est} unidades")

        Spacer(modifier = Modifier.height(24.dp))

        // Botão para voltar à lista de produtos
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { navController.popBackStack() }
        ) {
            Text(text = "Voltar à Lista de Produtos", color = Color.White)
        }
    }
}


@Composable
fun TelaEstatisticas(navController: NavController) {
    val valorTotalEstoque = Estoque.calcularValorTotalEstoque()
    val quantidadeTotalProdutos = Estoque.calcularQuantidadeTotalProdutos()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ESTATÍSTICAS DO ESTOQUE",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Exibe o valor total do estoque
        Text(
            text = "Valor Total: R$ %.2f".format(valorTotalEstoque),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Exibe a quantidade total de produtos
        Text(
            text = "Quantidade Total: $quantidadeTotalProdutos unidades",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botão para voltar à lista de produtos
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.popBackStack() }
        ) {
            Text(text = "Voltar à Lista de Produtos")
        }
    }
}


@Composable
fun Main(){
    // Controlador da navegação
    val navController = rememberNavController()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Configurando Navegação
        NavHost(navController = navController, startDestination = "telaCadastro") {
            composable("telaCadastro") { TelaCadastroProduto(navController) }
            composable("telaLista") { TelaListaProdutos(navController) }
            composable("telaDetalhes/{produtoJson}") { backStackEntry ->
                val produtoJson = backStackEntry.arguments?.getString("produtoJson")
                TelaDetalhesProduto(navController, produtoJson)
            }
            composable("telaEstatisticas") { TelaEstatisticas(navController) }
        }

    }
}