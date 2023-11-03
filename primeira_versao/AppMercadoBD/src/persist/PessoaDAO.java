package persist;

import entity.Pessoa;
import entity.Usuario;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import util.TipoUsuario;

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
        if (obj instanceof Pessoa) {
            Pessoa p = (Pessoa) obj;
            String nome = p.getNome();
            String cpf = p.getCpf();
            String dataNascimento = p.getDataNascimentoString();
            String email = p.getUser().getLogin();
            String senha = p.getUser().getSenha();
            Enum tipo = p.getUser().getTipoUsuario();
            int tipoUser = tipo.ordinal();
            String sql = "INSERT INTO usuários (cpf, dataNascimento, email, nome, senha, tipoUsuario)"
                    + "VALUES ( ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement pstmt = conexao.prepareStatement(sql);
                pstmt.setString(1, cpf);
                pstmt.setString(2, dataNascimento);
                pstmt.setString(3, email);
                pstmt.setString(4, nome);
                pstmt.setString(5, senha);
                pstmt.setInt(6, tipoUser);
                pstmt.executeUpdate();
                return true;
            } catch (SQLException sqe) {
                System.out.println("Erro = " + sqe);
            }
        }
        return false;
    }

    @Override
    //Em obj estará o cpf
    public Object read(Object obj) {
        Objects.requireNonNull(obj);
        if (obj instanceof String) {
            try {
                String cpf = (String) obj;
                String sql = "SELECT * FROM usuários WHERE cpf = '" + cpf + "'";
                Statement stmt = conexao.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.isBeforeFirst()) {
                    rs.next();
                    String nome = rs.getString(3);
                    String data = rs.getString(4);
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate dataNascimento = LocalDate.parse(data, formato);
                    String email = rs.getString(2);
                    String senha = rs.getString(5);
                    int tipo = rs.getInt(6);
                    Enum tipoUser = TipoUsuario.COMUN;
                    if (tipo != 0) {
                        tipoUser = TipoUsuario.ADMIN;
                    }
                    Usuario user = new Usuario(email, senha);
                    user.setTipoUsuario(tipoUser);
                    Pessoa p = new Pessoa(cpf, nome, dataNascimento);
                    p.setUser(user);
                    return p;
                }
            } catch (SQLException ex) {
                System.out.println("Erro = " + ex);
            }
        }
        return null;
    }

    @Override
    public boolean update(Object obj) {
        Objects.requireNonNull(obj);
        if (obj instanceof Pessoa) {
            try {
                Pessoa p = (Pessoa) obj;
                String sql = "UPDATE usuários SET nome = ?, dataNascimento = ?, email = ?, senha = ?, tipoUsuario = ? WHERE cpf = ?";
                PreparedStatement pstmt = conexao.prepareStatement(sql);
                pstmt.setString(1, p.getNome());
                pstmt.setString(2, p.getDataNascimentoString());
                pstmt.setString(3, p.getUser().getLogin());
                pstmt.setString(4, p.getUser().getSenha());
                pstmt.setInt(5, p.getUser().getTipoUsuario().ordinal());
                pstmt.setString(6, p.getCpf());
                pstmt.executeUpdate();
                return true;
            } catch (SQLException ex) {
                System.out.println("Erro = " + ex);
            }
        }
        return false;
    }

    @Override
    public boolean delete(Object obj) {
        Objects.requireNonNull(obj);
        if (obj instanceof String) {
            try {
                String cpf = (String) obj;
                String sql = "DELETE FROM usuários WHERE cpf = '" + cpf + "'";
                Statement stmt = conexao.createStatement();
                int nreg = stmt.executeUpdate(sql);
                if (nreg > 0) {
                    return true;
                }
            } catch (SQLException ex) {
                System.out.println("Erro = " + ex);
            }
        }
        return false;
    }

}