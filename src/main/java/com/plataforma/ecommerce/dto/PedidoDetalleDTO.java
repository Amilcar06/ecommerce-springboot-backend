package com.plataforma.ecommerce.dto;

import com.plataforma.ecommerce.model.PedidoDetalle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class PedidoDetalleDTO {

    private Long productoId;
    private String nombreProducto;
    private int cantidad;
    private BigDecimal precioUnitario;

    public static PedidoDetalleDTO fromEntity(PedidoDetalle detalle) {
        return PedidoDetalleDTO.builder()
                .productoId(detalle.getProducto().getId())
                .nombreProducto(detalle.getProducto().getNombre())
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario())
                .build();
    }
}
