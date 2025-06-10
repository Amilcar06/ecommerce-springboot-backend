package com.plataforma.ecommerce.dto;

import com.plataforma.ecommerce.model.Pedido;
import com.plataforma.ecommerce.model.enums.EstadoPedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoDTO {

    private Long id;

    @NotNull(message = "La fecha del pedido es obligatoria")
    private LocalDateTime fecha;
    
    @NotNull(message = "El estado del pedido es obligatorio")
    private EstadoPedido estado;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @NotEmpty(message = "Debe haber al menos un detalle de pedido")
    @Valid
    private List<PedidoDetalleDTO> detalles;

    public static PedidoDTO fromEntity(Pedido pedido) {
        return PedidoDTO.builder()
                .id(pedido.getId())
                .fecha(pedido.getFecha())
                .estado(pedido.getEstado())
                .usuarioId(pedido.getUsuario().getId())
                .detalles(
                        pedido.getDetalles().stream()
                                .map(PedidoDetalleDTO::fromEntity)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
