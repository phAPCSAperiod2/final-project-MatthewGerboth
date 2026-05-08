Dungeon Explorer
A Text‑Based Java RPG Adventure

1. Project Description
Dungeon Explorer is a turn‑based, text‑based RPG where the player explores randomly generated rooms, battles enemies, collects loot, manages an inventory, and equips weapons and shields to grow stronger. The goal is to survive as long as possible while progressing through increasingly difficult encounters. Players can attack, use potions, open their inventory without losing a turn, and make strategic decisions that affect their survival. This project demonstrates object‑oriented programming, class interaction, abstraction, and dynamic data structures in Java.

2. Features
Turn‑based combat system with fair turn logic

Enemy difficulty scaling based on rooms cleared

Randomized room generation (monster, boss, heal, shop, loot)

Inventory system using ArrayLists

Stacked inventory display with a 2D grid layout

Potion system with heal amounts displayed

Equipment system for swords and shields

Gold system with loot rewards

Menu‑driven user interaction

Clean class structure following AP CSA OOP principles

3. Code Structure and Design
Dungeon Explorer is organized into multiple classes, each with a clear responsibility:

App – Starts the game, handles player creation, and runs the main loop

Player – Stores player stats, inventory, equipment, combat actions, and item usage

Enemy – Generates enemies with scaling stats and boss variants

Room – Creates random room types and triggers events

CombatSystem – Handles turn‑based combat and player choices

Item (abstract) – Base class for all items

Sword, Shield, Potion – Concrete item types with unique behaviors

LootPool – Generates random loot items

Object Interaction
App creates a Player and repeatedly generates Room objects

Room may create an Enemy or loot, and may call CombatSystem

CombatSystem interacts with both Player and Enemy

Player manages its own inventory and equipment

Data Structures
ArrayList<Item> stores the player’s inventory

Stacked inventory is generated using temporary ArrayLists

Objects interact through method calls and shared references

<img width="1536" height="1024" alt="Copilot_20260507_103608" src="https://github.com/user-attachments/assets/28df89da-5a3f-444f-9b1f-a0d090d1c311" />

4. How to Run the Program
Follow these steps to compile and run Dungeon Explorer:

Ensure all .java files are in the same project folder

Open a terminal or use your IDE’s build/run tools

Compile the program:

Code
javac App.java
Run the program:

Code
java App
When prompted, enter your player name

Follow on‑screen menu options such as:

Attack

Use Potion

Open Inventory

Run

Continue exploring rooms until you choose to stop or your health reaches zero

5. Development Process
One major challenge was designing the inventory system so that items could stack and display in a 2D grid while still allowing individual items (like potions) to be used correctly. This was solved by generating a temporary “stacked view” of the inventory while keeping the ArrayList unchanged.
If more time were available, I would add a equipment comparison.

6. Use of AI Tools
AI Assistance:
Microsoft Copilot was used to help brainstorm class design ideas, debug logic errors, and refine documentation. All AI‑suggested code was reviewed, tested, and fully understood by the author. No AI tool wrote the project independently; all final logic and structure were implemented and verified by the student.

7. Author Information
Matthew  
AP Computer Science A
From this project, I learned how to design a multi‑class Java program, manage object interactions, and build a functional game using OOP principles.
