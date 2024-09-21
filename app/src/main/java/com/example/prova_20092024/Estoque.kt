package com.example.prova_20092024

object Estoque {
    val listaProdutos = mutableListOf<Produto>()

    // Método para adicionar um produto ao estoque
    fun adicionarProduto(produto: Produto): Boolean {
        return if (produto.quantidade_est >= 1 && produto.preco >= 0) {
            listaProdutos.add(produto)
            true
        } else {
            false
        }
    }

    // Método para calcular o valor total do estoque
    fun calcularValorTotalEstoque(): Double {
        var valorTotal = 0.0
        for (produto in listaProdutos) {
            valorTotal += produto.quantidade_est * produto.preco
        }
        return valorTotal
    }

    // Método para calcular a quantidade total de produtos em estoque
    fun calcularQuantidadeTotalProdutos(): Int {
        var quantidadeTotal = 0
        for (produto in listaProdutos) {
            quantidadeTotal += produto.quantidade_est
        }
        return quantidadeTotal
    }
}
