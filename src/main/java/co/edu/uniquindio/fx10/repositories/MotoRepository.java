package co.edu.uniquindio.fx10.repositories;

import co.edu.uniquindio.fx10.models.Moto;

import java.util.ArrayList;


public class MotoRepository {
    private static MotoRepository instancia;
    private ArrayList<Moto> motos;

    private MotoRepository() {
        motos = new ArrayList<>();
        cargarDatosEjemplo();
    }


    public static MotoRepository getInstancia() {
        if (instancia == null) {
            instancia = new MotoRepository();
        }
        return instancia;
    }


    private void cargarDatosEjemplo() {
        motos.add(new Moto("KCM48G", "YAMAHA", "2023"));
        motos.add(new Moto("SMO11U", "HONDA", "2022"));
        motos.add(new Moto("PPZ03D", "AKT", "2025"));
    }


    public ArrayList<Moto> getProductos() {

        return motos;
    }


    public void agregarMoto(Moto moto) {

        motos.add(moto);

    }


    public boolean eliminarMoto(Moto moto) {

        return motos.remove(moto);

    }


    public int getCantidadMotos() {
        return motos.size();
    }
}

