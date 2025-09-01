# README-backend.md

# Motherlode — Backend

**Stack:** Spring Boot 3.5.5 · Java 17 · Spring Web · Spring Security (JWT) · Validation · JPA/Hibernate · MariaDB embebido (MariaDB4j)

**API base:** `http://localhost:8082`  
**BD embebida (persistente):** MariaDB en `localhost:3307`, datos en `./database/data`

---

## 1) Qué es esto

Servicio REST que gestiona:

- Registro y login por **JWT**
- Perfil del usuario (`/user/me`)
- Cambios de **email** y **contraseña**
- Ajustes de integración (**Capital.com**: API Key + password)

Funciona **sin Docker**: levanta **MariaDB embebido** y **persiste** en la carpeta del proyecto.

---

## 2) Cómo se ejecuta

```bash
# Desarrollo
mvn spring-boot:run

# Producción local (jar)
mvn clean package -DskipTests
java -jar target/Motherlode-back-0.0.1-SNAPSHOT.jar
