# CMPT276S25_group9

# ![Goblins Keep](src/main/resources/UI_img/titleScreen.png)

## About the Game
**Goblins Keep** is a 2D tile-based escape game where players navigate through a maze-like castle while avoiding goblins and traps. Players must solve puzzles, collect items, increase their score, and survive to escape the castle.

### Key Features:
- **AI Movement**: Goblins use A* pathfinding and random movement for dynamic behavior.
- **Scoring System**: Track your progress and compete for high scores.
- **Interactive Environment**: Levers, doors, and traps add complexity.
- **Polished Visuals**: Smooth sprite transitions, animations, and environmental effects.
- **Game States & UI**: Includes a main menu, in-game HUD, pause functionality, and a game-over screen.

---

## Prerequisites
Before building or running the game, ensure you have the following installed:
- [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html) (Version 23 or higher)
- [Apache Maven](https://maven.apache.org/download.cgi) (Version 3.6.0 or higher)

---

## Build and Play

### Build from Source
You can build the game from source using Maven:

1. **Build the Game**:
    ```bash
    mvn clean compile assembly:single
    ```

2. **Run the Game**:
    ```bash
    java -jar target/GoblinsKeep-1.0.jar
    ```

The resulting JAR file will be located in the `${basedir}/target` directory.

---

## Test

To run the unit and integration tests, use the following command:
```bash
mvn test
```

---

## Authors
Developed with love by:
- Maxime Nereyabagabo
- Hugo Majafi
- Arun Paudel
- Srinivas Suggu

---

## Acknowledgements
We would like to express our gratitude to the following individuals and resources for their guidance and support during the development of this game:
- [Dr. Saba Alimadadi](http://www.sfu.ca/~saba/) - For teaching us essential software development principles.
- [RyiSnow](https://www.youtube.com/@RyiSnow) - For providing tutorials on game development in Java.
