@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // ou toute autre mapping de votre API
                .allowedOrigins("http://localhost:3000")  // Ajoutez ici l'origine de votre application frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Ajoutez ici les méthodes HTTP autorisées
                .allowCredentials(true);  // Autorise les cookies et les en-têtes d'authentification
    }
}
