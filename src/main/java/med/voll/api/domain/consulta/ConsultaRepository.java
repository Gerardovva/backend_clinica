package med.voll.api.domain.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Boolean existsByPacienteIdAndFechaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    Boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecha);

/*
    @Query("SELECT c.id, m.nombre, p.nombre, m.especialidad, c.fecha " +
            "FROM Consulta c " +
            "JOIN c.medico m " +
            "JOIN c.paciente p")
    Page<DetalleConsulta> consultarCita(Pageable pageable);*/

    @Query("SELECT c.id, m.nombre, p.nombre, m.especialidad, c.fecha " +
            "FROM Consulta c " +
            "JOIN c.medico m " +
            "JOIN c.paciente p")
    Page<Object[]> consultarCita(Pageable pageable);

}
