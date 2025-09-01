package io.mremptiness.motherlodeback.config;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class EmbeddedMariaDbConfig {

    private DB db;

    public EmbeddedMariaDbConfig() {
        try {
            startEmbeddedDatabase();
        } catch (ManagedProcessException e) {
            System.err.println("[ERROR] No se pudo iniciar MariaDB embebido:");
            e.printStackTrace();
        }
    }

    private void startEmbeddedDatabase() throws ManagedProcessException {
        DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
        config.setPort(3307);                   // Puerto del embebido
        config.setDataDir("./database/data");   // Carpeta local para datos (persistencia opcional)

        System.out.println("[INFO] Iniciando MariaDB embebido en puerto " + config.getPort());
        db = DB.newEmbeddedDB(config.build());
        db.start();
        db.createDB("motherlode");              // <-- nombre de BBDD para Motherlode
        System.out.println("[INFO] MariaDB embebido listo y base de datos creada.");
    }

    @PreDestroy
    public void stopDatabase() {
        try {
            if (db != null) {
                System.out.println("[INFO] Deteniendo MariaDB embebido...");
                db.stop();
                System.out.println("[INFO] MariaDB embebido detenido.");
            }
        } catch (ManagedProcessException e) {
            System.err.println("[ERROR] No se pudo detener MariaDB embebido:");
            e.printStackTrace();
        }
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mariadb://localhost:3307/motherlode")  // <-- URL actualizada
                .username("root")
                .password("")
                .driverClassName("org.mariadb.jdbc.Driver")
                .build();
    }
}
