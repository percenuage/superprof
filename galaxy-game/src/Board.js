'use strict';

import Cell from './Cell.js';

const MAX_ALIEN_STEPS = 3;

export default class Board {
    constructor(rows, cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = [];
        this.aliens = [];
        this.initCells();
    }

    initCells() {
        for (let i = 0; i < this.rows; i++) {
            this.cells.push([]);
            for (let j = 0; j < this.cols; j++) {
                this.cells[i].push(new Cell(i, j, this));
            }
        }
    }

    addRandomBlockingCells(number) {
        for (let i = 0; i < number; i++) {
            let cell = this.getRandomVoidCell();
            cell.block = true;
        }
    }

    addAlien(alien) {
        let cell = this.getRandomVoidCell();
        cell.setAlien(alien);
        this.aliens.push(alien);
    }

    addGun(gun) {
        let cell = this.getRandomVoidCell();
        cell.setGun(gun);
    }

    getRandomVoidCell() {
        let row, col;
        do {
            row = Board.getRandomIntExclusive(0, this.rows);
            col = Board.getRandomIntExclusive(0, this.cols);
        } while (!this.cells[row][col].isVoid());
        return this.cells[row][col];
    }

    getRelativeCell(origin, row, col) {
        let cell = null;
        try {
            cell = this.cells[origin.row + row][origin.col + col] || origin;
        } catch (e) {
            return origin;
        }
        return cell;
    }

    nextAlien() {
        let alien = this.aliens.shift();
        this.aliens.push(alien);
        this.resetAlienPaths();
        this.enableAlienPaths(alien.cell, MAX_ALIEN_STEPS);
        return alien;
    }

    moveAlien(alien, row, col) {
        let cell = this.getRelativeCell(alien.cell, row, col);
        if (alien.canMove(cell)) {
            alien.moveTo(cell);
        }
    }

    enableAlienPaths(origin, steps) {
        if (steps < 0) return;
        origin.active = !origin.block;
        !origin.block && this.enableAlienPaths(this.getRelativeCell(origin, -1, 0), steps - 1);
        !origin.block && this.enableAlienPaths(this.getRelativeCell(origin, 0, 1), steps - 1);
        !origin.block && this.enableAlienPaths(this.getRelativeCell(origin, 1, 0), steps - 1);
        !origin.block && this.enableAlienPaths(this.getRelativeCell(origin, 0, -1), steps - 1);
    }

    resetAlienPaths() {
        for (let i = 0; i < this.rows; i++) {
            for (let j = 0; j < this.cols; j++) {
                this.cells[i][j].active = false;
            }
        }
    }

    paint(context) {
        for (let i = 0; i < this.rows; i++) {
            for (let j = 0; j < this.cols; j++) {
                this.cells[i][j].paint(context);
            }
        }
    }

    static getRandomIntExclusive(min, max) {
        min = Math.ceil(min);
        max = Math.floor(max);
        return Math.floor(Math.random() * (max - min)) + min;
    }

    static get MAX_ALIEN_STEPS() {
        return MAX_ALIEN_STEPS;
    }
}