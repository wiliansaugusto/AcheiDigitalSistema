package digital.achei.api.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tag_humano")
@Getter
@Setter
public class TagHumanoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag")
    Long idTag;

    @ManyToOne
    @JoinColumn(name = "id_protegido")
    ProtegidoHumanoDTO protegido;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    UsuarioDTO usuario;
}
