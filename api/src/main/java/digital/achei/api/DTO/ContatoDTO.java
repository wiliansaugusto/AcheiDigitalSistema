package digital.achei.api.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Entity
@Table(name = "contato")
@Getter
@Setter
public class ContatoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contato")
    Long idContato;

    @NotBlank
    @Length(min =4, max = 100)
    String tpContato;

    @NotBlank
    @Size(min =4, max = 100)
    String descContato;

    @Column(name = "dataAlteracao")
    @Temporal(TemporalType.TIMESTAMP)
    Date dataAlteracao = new Date();

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    UsuarioDTO usuario;


}
