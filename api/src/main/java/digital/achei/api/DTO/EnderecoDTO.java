package digital.achei.api.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Entity
@Table(name = "endereco")
@Getter
@Setter
public class EnderecoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    Long idEndereco;

    @Length(min = 3, max = 100)
    String tpEndereco;

    @Size(min = 4, max = 200)
    String logradouro;

    @Size(min = 1, max = 20)
    String numero;

    String complemento;

    @Size(min = 4, max = 20)
    String bairro;

    @Size(min = 4, max = 20)
    String cidade;

    @Length(min = 2, max = 2)
    String estadoSigla;

    @Size(min = 8, max = 9)
    String cep;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    UsuarioDTO usuario;

    @Column(name = "dataAlteracao")
    @Temporal(TemporalType.TIMESTAMP)
    Date dataAlteracao = new Date();

}
