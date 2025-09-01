package io.mremptiness.motherlodeback.config;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
public class EmbeddedMariaDbConfig {

    private DB db;

    public EmbeddedMariaDbConfig() {
        try {
            DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
            config.setPort(3307);
            config.setDataDir("./database/data");
            db = DB.newEmbeddedDB(config.build());
            db.start();
            db.createDB("motherlode");
            System.out.println("[INFO] MariaDB embebido listo (persistente).");
        } catch (ManagedProcessException e) {
            System.err.println("[ERROR] No se pudo iniciar MariaDB embebido:");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
                .url("jdbc:mariadb://localhost:3307/motherlode")
                .username("root")
                .password("")
                .driverClassName("org.mariadb.jdbc.Driver")
                .build();
    }
}
