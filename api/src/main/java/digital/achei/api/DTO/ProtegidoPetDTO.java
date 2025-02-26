package digital.achei.api.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
@Entity
@Table(name = "protegido_pet")
@Getter
@Setter
public class ProtegidoPetDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idProtegidoPet;

    @NotNull(message = "Nome é obrigátorio")
    @NotBlank(message = "Nome é obrigátorio")
    String nomeProtegidoPet;

    @Past(message = "A data de nascimento dever estar no passado")
    @NotNull
    LocalDate dataNascimento;

    @Digits(integer = 2, fraction = 3, message = "Peso invalido deve ser um numero com 3 interios e 3 decimais")
    BigDecimal peso;

    String patologias;

    String alergias;

    String usoMedicacoes;

    String observacoesGerais;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    String imagem;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    UsuarioDTO usuario;

    @Column(name = "dataAlteracao")
    @Temporal(TemporalType.TIMESTAMP)
    Date dataAlteracao = new Date();

}
