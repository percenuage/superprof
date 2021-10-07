#!/usr/bin/env node

'use strict';

/* ---------- ENVIRONMENT VARIABLES ---------- */

require('dotenv').config();

/* ---------- MAIN ---------- */

const SERVER = '../server';
const App = require(SERVER + '/app');
const Database = require(SERVER + '/database');

Database.connect(process.env.MONGO_URI).then(() => {
    App.start(process.env.PORT);
});
