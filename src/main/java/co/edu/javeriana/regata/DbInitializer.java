package co.edu.javeriana.regata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import co.edu.javeriana.regata.domain.*;
import co.edu.javeriana.regata.repository.JugadorRepository;
import co.edu.javeriana.regata.repository.ModeloBarcoRepository;
import co.edu.javeriana.regata.repository.BarcoRepository;
import co.edu.javeriana.regata.repository.MapaRepository;
import co.edu.javeriana.regata.repository.CeldaRepository;

@Component
public class DbInitializer implements CommandLineRunner {

    private final JugadorRepository jugadorRepo;
    private final ModeloBarcoRepository modeloRepo;
    private final BarcoRepository barcoRepo;
    private final MapaRepository mapaRepo;
    private final CeldaRepository celdaRepo;

    public DbInitializer(JugadorRepository jugadorRepo, ModeloBarcoRepository modeloRepo,
                         BarcoRepository barcoRepo, MapaRepository mapaRepo, CeldaRepository celdaRepo){
        this.jugadorRepo = jugadorRepo; this.modeloRepo = modeloRepo; this.barcoRepo = barcoRepo;
        this.mapaRepo = mapaRepo; this.celdaRepo = celdaRepo;
    }

    @Override @Transactional
    public void run(String... args) throws Exception {
        if(jugadorRepo.count() > 0) return;

        // 5 jugadores
        List<Jugador> jugadores = new ArrayList<>();
        for(int i=1;i<=5;i++){
            Jugador j = new Jugador();
            j.setNombre("Jugador " + i);
            j.setEmail("jugador"+i+"@example.com");
            jugadores.add(jugadorRepo.save(j));
        }

        // 10 modelos con colores
        List<ModeloBarco> modelos = new ArrayList<>();
        String[] colors = {"#FF5733","#33FF57","#3357FF","#F1C40F","#9B59B6","#1ABC9C","#E67E22","#2ECC71","#3498DB","#E74C3C"};
        for(int i=1;i<=10;i++){
            ModeloBarco m = new ModeloBarco();
            m.setNombre("Modelo " + i);
            m.setColorHex(colors[(i-1)%colors.length]);
            modelos.add(modeloRepo.save(m));
        }

        // 50 barcos (10 por jugador)
        int idx = 0;
        for(Jugador j : jugadores){
            for(int k=0;k<10;k++){
                Barco b = new Barco();
                b.setJugador(j);
                b.setModelo(modelos.get((idx++) % modelos.size()));
                b.setPosX(0); b.setPosY(k % 5);
                b.setVelX(0); b.setVelY(0);
                barcoRepo.save(b);
            }
        }

        // Mapa 20x10 con partida/meta y paredes aleatorias
        Mapa mapa = new Mapa();
        mapa.setNombre("Mapa de ejemplo");
        mapa.setAncho(20);
        mapa.setAlto(10);
        mapa = mapaRepo.save(mapa);

        Random rnd = new Random(7);
        for(int y=0;y<mapa.getAlto();y++){
            for(int x=0;x<mapa.getAncho();x++){
                Celda c = new Celda();
                c.setMapa(mapa);
                c.setxCoord(x);
                c.setyCoord(y);
                if(x==0) c.setTipo(TipoCelda.PARTIDA);
                else if(x==mapa.getAncho()-1) c.setTipo(TipoCelda.META);
                else c.setTipo(rnd.nextDouble()<0.12 ? TipoCelda.PARED : TipoCelda.AGUA);
                celdaRepo.save(c);
            }
        }
    }
}
