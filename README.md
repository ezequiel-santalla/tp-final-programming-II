# 🎾 Tennis Tournament Manager

**Tennis Tournament Manager** is a console application in Java to manage tennis tournaments. The application allows managing players, creating rounds, viewing matches and statistics, and maintaining an updated ranking of players in the tournament.


## 🚀 Features

- **Tournament Management**: Create and configure tournaments with name, location, and surface type.
- **Player Administration**: Add players to the tournament and view their statistics.
- **Rounds and Matches**: Manage rounds and matches for each tournament.
- **Player Statistics**: View detailed statistics for each player.
- **Player Ranking**: Display an updated ranking of players.
- **Data Export**: Export ranking and statistics in JSON format.


## 🛠 Project Structure

The project is organized into packages for easier maintenance and scalability:

- **`repository`**: Stores in JSON with `TournamentRepository`, `MatchRepository`, `PlayerRepository`.
- **`model`**: Contains the classes `Tournament`, `Round`, `Match`, `Result`, `Player`.
- **`service`**: Implements business logic in `TournamentService`, `MatchService`, `PlayerService`.
- **`enums`**: Defines enumerations like `ESurface`.
- **`exceptions`**: Handles tournament and player service exceptions, such as `TournamentService`.
- **`utils`**: Provides helper functions for data validation, formatting, and conversions to maintain reusable and organized code, such as `JSONConverter`.
- **`view`**: Manages user interface interactions, displaying tournament, player, and match data in a user-friendly format. We used: `Menu`, and `MenuHandler`.



## 📋 Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/ezequiel-santalla/tp-final-programming-II.git
   ```   

2. **Set up the environment**: Make sure you have [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) and an IDE like [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) or [Eclipse](https://www.eclipse.org/downloads/) installed.


3. **Compile the project**:
   - In IntelliJ or Eclipse: Import the project as a standard Java project.
   - Ensure the file structure is set up correctly so that classes compile without issues.


4. **Run the Project**:
   - Locate and run the main class `Main` in `src/main/java/`.


## 🔍 Key Features

### Tournament Management
- Create, configure, and view tournaments.
- Access tournament details like name, location, and surface type.

### Player Administration
- Add players with detailed statistics.
- Display a list of players in the tournament.

### Rounds and Matches
- Generate rounds automatically or allow manual creation.
- Display scheduled matches in each round.

### Player Ranking
- Rank players based on their match results.


## 📂 File Structure

```plaintext
tp-final-programming-II/
├── .idea/
├── data/
│   ├── backUp/
├── libs/
├── src/
│   ├── enums/
│   ├── exceptions/
│   ├── model/
│   ├── repository/
│   ├── service/
│   ├── test/
│   ├── utils/
│   ├── view/
│   ├── Main
├── UML/
```


## 📜 Contributions

Contributions are welcome! If you'd like to collaborate:

1. Fork the repository.
2. Create a branch for your feature (`git checkout -b feature/new-feature`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to your branch (`git push origin feature/new-feature`).
5. Open a Pull Request.


## 🛡️ License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.


## 📞 Contact

If you have any questions or comments, feel free to reach out at:

- 📧 Email: [ezequielasantalla@gmail.com](mailto:ezequielasantalla@gmail.com)


## 🎓 Developers

This project was developed by students from UTN in Mar del Plata, Argentina, in the programming program, completing their first year:

- Lang, Rodrigo - [GitHub](https://github.com/RodriLang)
- Santalla, Ezequiel - [GitHub](https://github.com/ezequiel-santalla)
- Torres, Jeremías - [GitHub](https://github.com/jere-12)
- Werner, Marcos - [GitHub](https://github.com/Marcoswerner92)

Thank you all for your collaboration!


