package rogerdev.gerenciadorcontatos.com.br.gerenciadordecontatos;

/**
 * Created by rogerfernandez on 21/03/15.
 */
public class Contato {

    private String nome, endereco, numero, email;
    private int id;

    public Contato(int id, String nome, String endereco, String numero, String email){
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.numero = numero;
        this.email = email;

    }

    public int getId() { return id; }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getNumero() {
        return numero;
    }

    public String getEmail() {
        return email;
    }
}
