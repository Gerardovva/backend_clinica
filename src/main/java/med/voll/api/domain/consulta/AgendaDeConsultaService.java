package med.voll.api.domain.consulta;


import med.voll.api.domain.consulta.cancelacion.ValidadorCancelamientoDeConsulta;
import med.voll.api.domain.consulta.validaciones.DatosCancelamientoConsulta;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@SuppressWarnings("all")
public class AgendaDeConsultaService {
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    List<ValidadorDeConsultas> validadores;

    @Autowired
    private List<ValidadorCancelamientoDeConsulta> validadoresCancelamiento;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos){

        if(!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("este id para el paciente no fue encontrado");
        }

        if(datos.idMedico()!=null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("este id para el medico no fue encontrado");
        }

        validadores.forEach(v-> v.validar(datos));

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var medico = seleccionarMedico(datos);

        if(medico==null){
            throw new ValidacionDeIntegridad("no existen medicos disponibles para este horario y especialidad");
        }

        var consulta = new Consulta(medico,paciente,datos.fecha());

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);

    }

    public void cancelar(DatosCancelamientoConsulta datos) {
        if (!consultaRepository.existsById(datos.idConsulta())) {
            throw new ValidacionDeIntegridad("Id de la consulta informado no existe!");
        }

        validadoresCancelamiento.forEach(v -> v.validar(datos));

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if(datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()==null){
            throw new ValidacionDeIntegridad("debe seleccionarse una especialidad para el medico");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }

    public Page<DatosDetalleConsulta> consultar(Pageable paginacion) {
        return consultaRepository.findAll(paginacion).map(DatosDetalleConsulta::new);
    }


   /* public Page<DetalleConsulta> consultarCita(Pageable pageable) {
        return consultaRepository.consultarCita(pageable).map(DetalleConsulta::new);
    }*/

    public Page<DetalleConsulta> consultarCita(Pageable pageable) {
        Page<Object[]> resultados = consultaRepository.consultarCita(pageable);
        return resultados.map(obj -> new DetalleConsulta(
                (Long) obj[0],
                obj[1].toString(), // Convierte a String
                obj[2].toString(), // Convierte a String
                obj[3].toString(), // Convierte a String
                (LocalDateTime) obj[4]
        ));
    }

}

/*
*  @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    List<ValidadorDeConsultas> validadores;

    public void agendar(DatosAgendarConsulta datos) {
        if (!pacienteRepository.findById(datos.idPaciente()).isPresent()) {
            throw new ValidacionDeIntegridad("Este id para el paciente no fue encontrado");
        }
        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())) {
            throw new ValidacionDeIntegridad("Este id para el medico no fue encontrado");
        }

        //validaciones
        this.validadores.forEach(v -> v.validar(datos));


        Paciente paciente = pacienteRepository.findById(datos.idPaciente()).get();
        Medico medico = seleccionarMedico(datos);
        Consulta consulta = new Consulta(null, medico, paciente, datos.fecha());
        consultaRepository.save(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if (datos.idMedico() != null) {
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad() == null) {
            throw new ValidacionDeIntegridad("debe seleccionarse una especialidad para el medico");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), datos.fecha());
    }*/
