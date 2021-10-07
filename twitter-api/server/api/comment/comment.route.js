'use strict';

const Router = require('express').Router();
const CommentService = require('./comment.service');

Router.route('/')
    .get(async (req, res, next) => {
        const comments = await CommentService.getAll();
        res.json(comments);
    });

Router.route('/:id')
    .post(async (req, res, next) => {
        const {message} = req.body;
        const comment = await CommentService.create(req.user, req.params.id, message);
        res.json(comment);
    });

//TODO: delete comment



















module.exports = Router;
