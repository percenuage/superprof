# Speed Car Racing

## Subject

Créer une application console de « jeu de voiture en mode course contre la montre »
De manière générale une voiture est un objet qui se déplace à une certaine vitesse et a une couleur
L’application représente un circuit de course sur lequel 2 types de voitures spécifiques peuvent rouler :
•    Une voiture rapide(100km/h) mais dont les pièces tombent souvent en panne
•    Une voiture lente(50km/h) mais qui sous certaines conditions peut avoir des boosts de vitesse(120km/h pendant 2 cycle)
Faites-en sorte qu’une option de démarrage par ligne de commande permette au joueur de choisir son type de voiture et sa couleur.
Le cycle de jeu est le suivant :
La voiture choisie par le joueur avance d’une certaine distance en fonction de sa vitesse et du temps du cycle (un cycle simule 1 minute de course), ensuite sa capacité spéciale se met(potentiellement) en route puis l’application attend 2 secondes(afin de ne pas inonder de message l’utilisateur, mais la course continue) et le cycle recommence.
La voiture rapide est une voiture qui contient les pièces suivantes :
•    Un moteur
•    4 roues (avant droit, avant gauche, arrière droit, arrière gauche)
•    Un volant 
•    Des freins
Lorsqu’une voiture rapide roule il y a 60% de chance qu’une de ces pièces tombe en panne.Pour la réparer le joueur doit taper dans la console le nom de la pièce
La voiture lente a 30% de chance de permettre au joueur d’avoir un boost de vitesse.Lorsque le boost est activable le mot VITESSE est affiché dans la console avec les lettres mélangées.Si le joueur entre les lettres dans le même ordre en moins de 3 secondes le boost d’active.

La course s’arrête lorsque la voiture a parcouru 50 km, le joueur est alors notifié du temps qu’il a mis à parcourir cette distance.
La note se fera sur la propreté du code, les commentaires, l'architecture et les fonctionnalités qui auront été codées

## Conception

### Classes

#### Car::Abstract

Attributes: 

- race: Race
- color: string|enum
- speed: int
- distance: float
- specialProb: float [0;1]
- status: string|enum [HEALTHY, BOOST, BROKEN]

Methods:

- move
- special::abstract

#### FastCar : Car

Attributes: 

- pieces: list<Piece>

Methods:

- randomBreakPiece
- reparePiece

#### SlowCar : Car

Attributes: 

- boostRemainingCycle: int
- boostEnableLimit: int
- boostWord: string (VITESSE)

Methods:

- enableBoost

#### Piece

Attributes: 

- name: string|enum [motor, carWheels (FR, FL, BR, BL), steeringWheel, brakes]
- status: string|enum [HEALTHY, BROKEN]

Methods:

- break(): void
- repare(): void

#### Race

Attributes: 

- CYCLE_TIME: int (seconds) [public]
- WAITING_TIME: int (seconds) [public]
- MAX_DISTANCE: int (km) [public]
- cars: list<Car>
- cyclePhase: string|enum [RUN, SPECIAL, WAIT]

Methods:

- run
- enableSpecials
- wait
- stop