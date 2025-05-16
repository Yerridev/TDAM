package com.example.yerripc1.Models;

import java.util.List;

public class RptaObtenerUsuarios {
    private int status;
    private List<Usuarios> data;
    private String message;

    public String getMessage() {
        return message;
    }

    public List<Usuarios> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
