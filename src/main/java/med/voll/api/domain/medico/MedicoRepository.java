package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long > {

    Page<Medico> findByActivoTrue(Pageable paginacion);

    @Query(value = """
            select * from Medicos m
            where m.activo = 1 
            and
            m.especialidad=:especialidad 
            and
            m.id not in(  
                select c.medico_id from Consultas c
                where
                c.fecha=:fecha
            )
            order by rand()
            limit 1
            """,nativeQuery = true)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);


    @Query(value = """
            select m.activo 
            from Medicos m
            where m.id=:idMedico
            """,nativeQuery = true)
    Byte findActivoById(Long idMedico);

}
