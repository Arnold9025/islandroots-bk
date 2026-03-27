# Étape 1 : Build de l'application avec Maven
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Copier les fichiers du wrapper Maven et le pom.xml
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Donner les droits d'exécution au wrapper
RUN chmod +x mvnw

# Télécharger les dépendances hors ligne
RUN ./mvnw dependency:go-offline

# Copier le code source et builder le jar (sans exécuter les tests)
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Étape 2 : Exécution de l'application
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copier le .jar généré depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Exposer le port par défaut de Spring Boot
EXPOSE 8080

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
