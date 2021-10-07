'use strict';

const Passport = require('passport');
const LocalStrategy = require('passport-local').Strategy;

const UserService = require('./api/user/user.service');

const localStrategy = () => {

    Passport.serializeUser((user, done) => done(null, user));
    Passport.deserializeUser((user, done) => done(null, user));

    const localField = {
        usernameField: 'pseudo',
        passwordField: 'password',
        passReqToCallback: false,
    };

    const localCallback = async (pseudo, password, done) => {
        let user = await UserService.getByPseudo(pseudo).catch(err => done(err));
        if (user && UserService.comparePassword(password, user.password)) {
            done(null, user);
        } else {
            done(null, false, {message: 'Incorrect login or password.'});
        }
    };

    Passport.use('local', new LocalStrategy(localField, localCallback))
};

module.exports = localStrategy;
