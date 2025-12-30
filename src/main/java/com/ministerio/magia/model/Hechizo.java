package com.ministerio.magia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hechizo {
    private String id;
    private String nombre;
    private String tipo;
    private Integer nivel;
}