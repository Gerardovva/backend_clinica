package med.voll.api.domain.consulta.cancelacion;

import med.voll.api.domain.consulta.validaciones.DatosCancelamientoConsulta;

public interface ValidadorCancelamientoDeConsulta {
    void validar(DatosCancelamientoConsulta datos);
}
