'use strict';

import Board from './Board.js';
import Alien from './Alien.js';
import Gun from './Gun.js';

/* MODELS */
let board = new Board(10, 10);
board.addRandomBlockingCells(10);
board.addAlien(new Alien('valentina'));
board.addAlien(new Alien('alien'));
board.addGun(new Gun('laser2', 20));
board.addGun(new Gun('laser3', 30));
board.addGun(new Gun('laser4', 40));

let currentAlien = board.nextAlien();
let count = 0;

let context = document.querySelector('canvas').getContext('2d');
board.paint(context);

/* CONTROLLERS */
document.onkeyup = e => {
    switch (e.key) {
        case 'ArrowUp':
            board.moveAlien(currentAlien, -1, 0);
            break;
        case 'ArrowRight':
            board.moveAlien(currentAlien, 0, 1);
            break;
        case 'ArrowDown':
            board.moveAlien(currentAlien, 1, 0);
            break;
        case 'ArrowLeft':
            board.moveAlien(currentAlien, 0, -1);
            break;
        default:
            break;
    }
    if (++count >= Board.MAX_ALIEN_STEPS) {
        currentAlien = board.nextAlien();
        count = 0;
    }
    board.paint(context);
};