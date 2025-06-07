package com.plataforma.ecommerce.dto;

import com.plataforma.ecommerce.model.PedidoDetalle;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoDetalleDTO {

    @NotNull(message = "El ID del producto no puede ser nulo")
    private Long productoId;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombreProducto;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidad;

    @NotNull(message = "El precio unitario no puede ser nulo")
    @Positive(message = "El precio unitario debe ser positivo")
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
