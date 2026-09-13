# POO - SpotifUM

Console-based music streaming application developed in Java for the **Object-Oriented Programming** course during the second year of my Bachelor's degree in Software Engineering (2025/2026).

The project simulates a Spotify-like platform, with users, music, albums, playlists and playback management.

**Grade:** 17/20

## Authors

- Francisco Contente (https://github.com/contente13)
- Gustavo Braga (https://github.com/gustavocbraga)
- Lucas Pinto (https://github.com/LPP-0)

## Running the Application

Java 8 or newer is recommended. Run the following commands from the repository root:

```bash
rm -rf out/production
mkdir -p out/production
javac -d out/production $(find src -name "*.java")
java -cp out/production SpotifUM
```

The application saves its state in `estado.dat` in the repository root.