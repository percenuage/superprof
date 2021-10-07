'use strict';

const Router = require('express').Router();
const Passport = require('passport');
const Bcrypt = require('bcryptjs');

Router.route('/login')
    .post(Passport.authenticate('local'), async (req, res) => {
        res.json(req.user);
    });

Router.route('/logout')
    .get((req, res) => {
        req.logout();
        res.redirect('/');
    });

Router.route('/me')
    .get((req, res) => {
        res.json(req.user || 'No authenticated user');
    });

Router.route('/hash')
    .get((req, res) => {
        res.json({clear: req.query.password, hash: Bcrypt.hashSync(req.query.password)});
    });

module.exports = Router;
