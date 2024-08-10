package digital.achei.api.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "protegidoHumano")
@Getter
@Setter
public class ProtegidoHumanoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idProtegidoHumano;

    @NotNull(message = "Nome é obrigátorio")
    @NotBlank(message = "Nome é obrigátorio")
    String nomeProtegido;

    @Past(message = "A data de nascimento dever estar no passado")
    @NotNull
    LocalDate dataNascimento;

    @Digits(integer = 1, fraction = 2,message = "Altura invalida deve ser um numero com 1 interios e 2 decimais")
    BigDecimal altura;

    @Digits(integer = 3, fraction = 2, message = "Peso invalido deve ser um numero com 3 interios e 2 decimais")
    BigDecimal peso;

    String patolgias;

    String alergias;

    String usoMedicacoes;

    String observacoesGerais;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    UsuarioDTO usuario;

    @Column(name = "dataAlteracao")
    @Temporal(TemporalType.TIMESTAMP)
    Date dataAlteracao = new Date();
}
