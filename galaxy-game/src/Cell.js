'use strict';

const FILL_STYLE = 'white';
const STROKE_STYLE = '#3d3d3d';
const BLOCK_STYLE = '#3d3d3d';
const ACTIVE_STYLE = '#31ce31';

export default class Cell {

    constructor(row, col, board) {
        this.row = row;
        this.col = col;
        this.board = board;
        this.alien = null;
        this.gun = null;
        this.block = false;
        this.active = false;
    }

    setAlien(alien) {
        this.alien = alien;
        this.alien.cell = this;
    }

    setGun(gun) {
        this.gun = gun;
        this.gun.cell = this;
    }

    isVoid() {
        return !this.block && !this.alien && !this.gun;
    }

    paint(context) {
        const cellWidth = context.canvas.width / this.board.cols;
        const cellHeight = context.canvas.height / this.board.rows;
        context.strokeStyle = STROKE_STYLE;
        if (this.block) {
            context.fillStyle = BLOCK_STYLE;
        } else if (this.active) {
            context.fillStyle = ACTIVE_STYLE;
        } else {
            context.fillStyle = FILL_STYLE;
        }
        context.fillRect(cellWidth * this.col, cellHeight * this.row, cellWidth, cellHeight);
        context.strokeRect(cellWidth * this.col, cellHeight * this.row, cellWidth, cellHeight);
         if (this.alien)  {
            this.alien.paint(context);
        }
    }

}