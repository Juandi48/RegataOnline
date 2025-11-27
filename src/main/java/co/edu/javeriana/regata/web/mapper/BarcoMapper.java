// src/main/java/co/edu/javeriana/regata/web/mapper/BarcoMapper.java
package co.edu.javeriana.regata.web.mapper;

import co.edu.javeriana.regata.domain.Barco;
import co.edu.javeriana.regata.web.dto.BarcoDTO;
import org.springframework.stereotype.Component;

@Component
public class BarcoMapper {

    public BarcoDTO toDto(Barco b) {
        BarcoDTO dto = new BarcoDTO();

        dto.setId(b.getId());
        dto.setPosX(b.getPosX());
        dto.setPosY(b.getPosY());
        dto.setVelX(b.getVelX());
        dto.setVelY(b.getVelY());

        if (b.getJugador() != null) {
            dto.setJugadorId(b.getJugador().getId());
            dto.setNombreJugador(b.getJugador().getNombre());
        }

        if (b.getModelo() != null) {
            dto.setModeloId(b.getModelo().getId());
            dto.setNombreModelo(b.getModelo().getNombre());
            dto.setColorHex(b.getModelo().getColorHex());
        }

        return dto;
    }
}
