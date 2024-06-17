package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DetalleConsulta(
        Long id,
        String nombreMedico,
        String nombrePaciente,
        String especialidad,
        LocalDateTime fecha) {

    public DetalleConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getMedico().getNombre(),
                consulta.getPaciente().getNombre(),
                consulta.getMedico().getEspecialidad().toString(),
                consulta.getFecha());
    }
}

