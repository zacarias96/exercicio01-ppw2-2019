package br.edu.ifg.sistemacomercial.bean;

import br.edu.ifg.sistemacomercial.dao.CategoriaDAO;
import br.edu.ifg.sistemacomercial.dao.ProdutoDAO;
import br.edu.ifg.sistemacomercial.entity.Categoria;
import br.edu.ifg.sistemacomercial.entity.Produto;
import br.edu.ifg.sistemacomercial.util.JsfUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ProdutoBean extends JsfUtil{
    
    private Produto produto;
    private List<Produto> produtos;
    private Status statusTela;
    
    private ProdutoDAO produtoDAO;
    private CategoriaDAO categoriaDAO;

    
    private enum Status {
        INSERINDO,
        EDITANDO,
        PESQUISANDO
    }
    
    
    @PostConstruct
    public void init(){
        produto = new Produto();
        produtos = new ArrayList<>();   
        statusTela = Status.PESQUISANDO;
        produtoDAO = new ProdutoDAO();
        categoriaDAO = new CategoriaDAO();
    }
    
    public void novo(){
        statusTela = Status.INSERINDO;
        produto = new Produto();
    }

    public void adicionarProduto(){
        try {
            produtoDAO.salvar(produto);
            produto = new Produto();
            addMensagem("Produto adicionado com sucesso!");
            pesquisar();
        } catch (SQLException ex) {
            addMensagemErro(ex.getMessage());
        }
    }
    
    public void remover(Produto produto){
        try {
            produtoDAO.deletar(produto);
            produtos.remove(produto);
            addMensagem("Produto deletado com sucesso!");
        } catch (SQLException ex) {
            addMensagemErro(ex.getMessage());
        }
    }
    public void editar(Produto produto){
        this.produto = produto;
        statusTela = Status.EDITANDO;
               
    }
    
    public void pesquisar(){
        try {
            if(!statusTela.equals(Status.PESQUISANDO)){
                statusTela = Status.PESQUISANDO;
                return;
            }
            produtos = produtoDAO.listar();
            if(produtos == null || produtos.isEmpty()){
                addMensagemAviso("Nenhum produto cadastrado.");
            }
        } catch (SQLException ex) {
            addMensagemErro(ex.getMessage());
        }
    }
    
    public ProdutoBean() {
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
    

    public List<Categoria> getCategorias() {
        try {
            return categoriaDAO.listar();
        } catch (SQLException ex) {
            addMensagemErro(ex.getMessage());
            return null;
        }
    }
    
    public String getStatusTela() {
        return statusTela.name();
    }
    
}
