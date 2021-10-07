'use strict';

const Router = require('express').Router();
const UserService = require('./user.service');

Router.route('/')
    .get(async (req, res, next) => {
        let users = await UserService.getAll();
        res.json(users)
    })
    .post(async (req, res, next) => {
       let user = await UserService.create(req.body);
       res.json(user);
    });

Router.route('/:pseudo')
    .get(async (req, res, next) => {
        let user = await UserService.getByPseudo(req.params.pseudo);
        res.json(user);
    });

Router.route('/:pseudo/subscribe')
    .post(async (req, res , next) => {
        let user = await UserService.subscribe(req.user, req.params.pseudo);
        res.json(user);
    });

Router.route('/:pseudo/unsubscribe')
    .post(async (req, res , next) => {
        let user = await UserService.unsubscribe(req.user, req.params.pseudo);
        res.json(user);
    });

module.exports = Router;

