
# Châtop Back Release Openclassroom Study Projet

## 📍 Overview

Project Context

    Company: ChâTop, a real estate rental company in a tourist area.
    Role: Back-end Developer.
    Objective: Develop an online portal to connect potential tenants with property owners.

Email from Marco (Your Manager)

    Welcome: Introduction and welcome to the team.
    Mission: Implement the back-end for a seasonal rental portal, initially for the Basque coast, later expanding to all of France.
    Current State: An Angular app with mocked data using Mockoon.
    Collaboration: Work with Stéphanie, the front-end developer.
    Requirements: User authentication and API documentation using Swagger.

Resources Provided

    Contents: Mockoon environment, Postman collection, database schema in GitHub repository.
    Technical Specifications: Detailed project requirements and guidelines.

Deliverables

    Format: TXT or PDF file with a link to the GitHub repository.
    Contents: Back-end code, detailed documentation, installation procedures, database setup, Swagger URL.
    Naming Convention: For easy identification during evaluation.

Oral Presentation (Defense)

    Structure: 15 minutes presentation, 10 minutes discussion, 5 minutes debrief.
    Content: Technologies and tools used, development phases, challenges, Spring Security and JWT implementation.
    Interaction: Role-play discussion with the evaluator as Marco, focusing on route implementation, code quality, and challenges faced.

Skills Evaluated

    Main Focus: Developing a maintainable Java back-end for an application.

This project is a comprehensive exercise in back-end development, offering a chance to collaborate with a front-end team and demonstrate your ability to present your work effectively. Successfully completing this project will showcase your skills in creating a robust and secure back-end for a web application in a professional development setting.


```

├───build
│   ├───classes
│   │   └───java
│   │       └───main
│   │           └───com
│   │               └───chatapp
│   │                   └───backend
│   │                       ├───configuration
|   |                           AppConfig.java
|   |                           ApplicationConfig.java
|   |                           CloudinaryConfig.java
|   |                           JwtAuthenticationFilter.java
|   |                           SecurityConfiguration.java
│   │                       ├───controller
|   |                           GlobalExceptionHandIer.java
|   |                           LoginControIIer.java
|   |                           MeControIIer.java
|   |                           MessageControIIer.java
|   |                           Registercontroller.java
|   |                           RentalController.java
|   |                           UserControIIer.java
│   │                       ├───DTO
|   |                           AuthRequest.java
|   |                           AuthResponse.java
|   |                           CustomErrorResponse.java
|   |                           GetA11Renta1s.java
|   |                           JwtResponse.java
|   |                           LoginRequest.java
|   |                           MessageRequest.java
|   |                           MessageResponse.java
|   |                           RegisterRequest.java
|   |                           RentalDT0.java
|   |                           RentalRequest.java
|   |                           RentalResponse.java
|   |                           RentalsResponse.java
|   |                           UpdateUserRequest.java
│   │                       ├───entity
|   |                           Message.java
|   |                           Rental.java
|   |                           Role.java
|   |                           User.java
│   │                       ├───exceptions
|   |                           DatabaseErrorException.java
|   |                           EmailA1readyUsedException.java
|   |                           InvalidCredentialsException.java
|   |                           JwtAuthenticationException.java
|   |                           ResourceNotFoundException.java
|   |                           TokenNotFoundException.java
│   │                       ├───repository
|   |                           MessageRepository.java
|   |                           Renta1Repository.java
|   |                           UserRepository.java
│   │                       ├───services
|   |                           AuthenticationService.java
|   |                           CloudinaryService.java
|   |                           JWTservice.java
|   |                           MessagesService.java
|   |                           Renta1Service.java
|   |                           UserService.java
│   │                       └───Util
|   |                           SuccessResponse.java


```
## 🚀 Getting Started

***Dependencies***

Please ensure you have the following dependencies installed on your system:

    ℹ️ com.cloudinary:cloudinary-http44: Pour intégrer Cloudinary pour la gestion des images.
    ℹ️ org.springframework.boot:spring-boot-starter-data-jpa: Pour l'accès aux bases de données via JPA.
    ℹ️org.springframework.boot:spring-boot-starter-security: Pour ajouter la sécurité Spring, y compris l'authentification et l'autorisation.
    ℹ️org.springframework.boot:spring-boot-starter-web: Pour créer des applications web, y compris RESTful, utilisant Spring MVC.
    ℹ️org.springframework.boot:spring-boot-starter-oauth2-client et ...oauth2-resource-server: Pour l'intégration OAuth2.
    ℹ️com.auth0:java-jwt: Pour travailler avec JWT (JSON Web Tokens).
    ℹ️org.springdoc:springdoc-openapi-starter-webmvc-ui: Pour la documentation API avec Swagger/OpenAPI.
    ℹ️commons-io:commons-io: Fournit des utilitaires pour travailler avec IO (entrée/sortie).
    ℹ️io.jsonwebtoken:jjwt-api, ...jjwt-impl, ...jjwt-jackson: Pour la création et le traitement de JWT.
    ℹ️org.springframework.boot:spring-boot-starter-validation: Pour la validation des données.
    ℹ️org.projectlombok:lombok: Pour réduire le code boilerplate dans Java (comme les getters, setters, etc.).
    com.h2database:h2: Base de données en mémoire pour les tests.
    ℹ️javax.validation:validation-api: API pour la validation des beans.
    ℹ️org.springframework.boot:spring-boot-starter-test et ...spring-security-test: Pour les tests, y compris la sécurité.
    ℹ️com.mysql:mysql-connector-j: Connecteur JDBC pour MySQL.

### 🔧 Installation


1. Clone the ChâTop back repository:
```sh
git clone https://github.com/citizensyd/chatop-back
```

2. Change to the project directory:
```sh
cd chatop-back
```

3. Install the dependencies:
```sh
gradle build
```
4. Add a file dev.env with your definition like this :
```
DATABASE_URL=jdbc:mysql://localhost:3306/your_database?useSSL=false&serverTimezone=UTC
DATABASE_USERNAME=your databale name
DATABASE_PASSWORD=your password
JWT_KEY=your sha 256 key
CLOUDINARY_CLOUD_NAME=your cloudinary
CLOUDINARY_API_KEY=your api key
CLOUDINARY_API_SECRET=your api secret
```
---

## 🤝 Contributing

Contributions are welcome! Here are several ways you can contribute:

- **[Submit Pull Requests](https://github.com/citizensyd/telesport/blob/main/CONTRIBUTING.md)**: Review open PRs, and submit your own PRs.
- **[Join the Discussions](https://github.com/citizensyd/telesport/discussions)**: Share your insights, provide feedback, or ask questions.
- **[Report Issues](https://github.com/citizensyd/telesport/issues)**: Submit bugs found or log feature requests for CITIZENSYD.

#### *Contributing Guidelines*

<details closed>
<summary>Click to expand</summary>

1. **Fork the Repository**: Start by forking the project repository to your GitHub account.
2. **Clone Locally**: Clone the forked repository to your local machine using a Git client.
   ```sh
   git clone <your-forked-repo-url>
   ```
3. **Create a New Branch**: Always work on a new branch, giving it a descriptive name.
   ```sh
   git checkout -b new-feature-x
   ```
4. **Make Your Changes**: Develop and test your changes locally.
5. **Commit Your Changes**: Commit with a clear and concise message describing your updates.
   ```sh
   git commit -m 'Implemented new feature x.'
   ```
6. **Push to GitHub**: Push the changes to your forked repository.
   ```sh
   git push origin new-feature-x
   ```
7. **Submit a Pull Request**: Create a PR against the original project repository. Clearly describe the changes and their motivations.

Once your PR is reviewed and approved, it will be merged into the main branch.

</details>

---

## 📄 License


This project is protected under the MIT License. For more details, refer to the LICENSE file.

---

## 👏 Acknowledgments

- Special thanks to my mentor, Hichem, for his invaluable guidance and support. I'm 
