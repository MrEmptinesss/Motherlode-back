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
            DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
            config.setPort(3307);           // Puerto estable
            // 👇 NO usar setDataDir ni setBaseDir en Windows con MariaDB4j
            db = DB.newEmbeddedDB(config.build());
            db.start();
            try { db.createDB("motherlode"); } catch (ManagedProcessException ignore) {}
            System.out.println("[INFO] MariaDB embebido listo.");
        } catch (ManagedProcessException e) {
            System.err.println("[ERROR] No se pudo iniciar MariaDB embebido:");
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void stopDatabase() {
        try { if (db != null) db.stop(); } catch (ManagedProcessException e) { e.printStackTrace(); }
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
