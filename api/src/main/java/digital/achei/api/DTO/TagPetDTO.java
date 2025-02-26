package digital.achei.api.DTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tag_pet")
@Getter
@Setter
public class TagPetDTO {
    @Id
    @Column(name = "id_tag_pet")
    Long idTagPet;

    @ManyToOne
    @JoinColumn(name = "id_protegido_pet")
    ProtegidoPetDTO protegidoPet;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    UsuarioDTO usuario;
}

