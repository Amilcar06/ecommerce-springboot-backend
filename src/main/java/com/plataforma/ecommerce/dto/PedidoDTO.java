package com.plataforma.ecommerce.dto;

import com.plataforma.ecommerce.model.Pedido;
import com.plataforma.ecommerce.model.PedidoDetalle;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoDTO {

    private Long id;
    private LocalDateTime fecha;
    private Long usuarioId;
    private List<PedidoDetalleDTO> detalles;

    public static PedidoDTO fromEntity(Pedido pedido) {
        return PedidoDTO.builder()
                .id(pedido.getId())
                .fecha(pedido.getFecha())
                .usuarioId(pedido.getUsuario().getId())
                .detalles(
                        pedido.getDetalles().stream()
                                .map(PedidoDetalleDTO::fromEntity)
                                .collect(Collectors.toList())
                )
                .build();
    }
}