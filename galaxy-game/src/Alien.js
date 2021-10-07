'use strict';

import DATA from './data.js';

export default class Alien {
    constructor(name, gun, cell) {
        this.name = name;
        this.gun = gun;
        this.cell = cell;
    }

    canMove(cell) {
        return !cell.block && !cell.alien;
    }

    moveTo(cell) {
        this.cell.alien = null;
        this.cell = cell;
        this.cell.alien = this;
    }

    paint(context) {
        const cellWidth = context.canvas.width / this.cell.board.cols;
        const cellHeight = context.canvas.height / this.cell.board.rows;
        let img = new Image();
        img.src = DATA.aliens[this.name].image;
        img.onload = () => context.drawImage(img, cellWidth * this.cell.col, cellHeight * this.cell.row);

    }

}