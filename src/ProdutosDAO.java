/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void venderProduto(int idProduto){
    PreparedStatement pstmt = null;

    try {
        // Abrindo conexão com o banco
        conn = new conectaDAO().connectDB();

        // Query SQL para atualizar o status do produto
        String sql = "UPDATE produtos SET status = ? WHERE id = ?";

        // Preparando a consulta
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "Vendido"); // Novo status
        pstmt.setInt(2, idProduto);   // ID do produto

        // Executando a atualização
        int linhasAfetadas = pstmt.executeUpdate();

        if (linhasAfetadas > 0) {
            System.out.println("Produto atualizado para 'Vendido' com sucesso!");
        } else {
            System.out.println("Produto não encontrado ou já vendido.");
        }

    } catch (SQLException e) {
        System.err.println("Erro ao atualizar o produto: " + e.getMessage());
    } finally {
        // Fechando recursos
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Erro ao fechar recursos: " + e.getMessage());
        }
    }
    }
    
    public void cadastrarProduto (ProdutosDTO produto){
    PreparedStatement pstmt = null;

    try {
        // Abrindo a conexão
        conn = new conectaDAO().connectDB();

        // Query de inserção
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        // Criando o PreparedStatement
        pstmt = conn.prepareStatement(sql);

        // Substituindo os placeholders (?) pelos valores do objeto produto
        pstmt.setString(1, produto.getNome());
        pstmt.setInt(2, produto.getValor()); // Supondo que valor seja inteiro
        pstmt.setString(3, produto.getStatus());

        // Executando o comando
        pstmt.executeUpdate();

        // Mensagem de sucesso
        System.out.println("Produto cadastrado com sucesso!");
    } catch (SQLException e) {
        System.err.println("Erro ao cadastrar produto: " + e.getMessage());
    } finally {
        // Fechando os recursos
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Erro ao fechar recursos: " + e.getMessage());
        }
    }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        // Abrindo a conexão com o banco
        conn = new conectaDAO().connectDB();

        // Query para buscar os produtos
        String sql = "SELECT id, nome, valor, status FROM produtos";

        // Preparando a consulta
        pstmt = conn.prepareStatement(sql);

        // Executando a consulta
        rs = pstmt.executeQuery();

        // Iterando pelos resultados
        while (rs.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setValor(rs.getInt("valor")); // Certifique-se de que o tipo seja compatível
            produto.setStatus(rs.getString("status"));

            // Adicionando o produto à lista
            listagem.add(produto);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao listar produtos: " + e.getMessage());
    } finally {
        // Fechando os recursos
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Erro ao fechar recursos: " + e.getMessage());
        }
    }

    return listagem;
    }
    
    
    
        
}

