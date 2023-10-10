package persist;


import entity.Pessoa;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.sql.PreparedStatement;

/**
 *
 * @author andre
 */
public class PessoaDAO implements DAO {

    private static PessoaDAO pdao;
    private static Connection conexao;

    public static PessoaDAO getInstance() {
        if (pdao == null) {
            pdao = new PessoaDAO();
        }
        return pdao;
    }

    private PessoaDAO() {
        ConexaoBD conexaoBD;

        try {
            conexaoBD = ConexaoBD.getInstance();
            conexao = ConexaoBD.getConexao();
        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro = " + ex);
        } catch (SQLException ex) {
            //Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro = " + ex);
        }

    }

    @Override
    public boolean create(Object obj) {
        Objects.requireNonNull(obj);
        if(obj instanceof Pessoa){
            Pessoa p = (Pessoa) obj;
            String cpf = p.getCpf();
            String nome = p.getNome() + p.getSobrenome();
            String dataNascimento = p.getDataNascimentoString();
            String endereco = p.getEnderecoString();
            String login = p.getUser().getLogin();
            String senha = p.getUser().getSenha();
            Enum tipo = p.getUser().getTipoUsuario();
            int tipoUser = tipo.ordinal();
            String sql = "INSERT INTO usuario (cpf, endereco, nome, dataNascimento, email, senha, tipoUsuario)" 
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement pstmt = conexao.prepareStatement(sql);
                pstmt.setString(1, cpf);
                pstmt.setString(2, endereco);
                pstmt.setString(3, nome);
                pstmt.setString(4, dataNascimento);
                pstmt.setString(5, login);
                pstmt.setString(6, senha);
                pstmt.setInt(7, tipoUser);
                pstmt.executeUpdate();
                return true;
            } catch (SQLException sqe) {
            }
        }
        return false;
    }

    @Override
    public Object read(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
