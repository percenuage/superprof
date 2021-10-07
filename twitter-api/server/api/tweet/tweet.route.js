'use strict';

const Router = require('express').Router();
const TweetService = require('./tweet.service');

Router.route('/')
    .get(async (req, res, next) => {
        const tweet = await TweetService.getAll();
        res.json(tweet);
    })
    .post(async (req, res, next) => {
        const tweet = await TweetService.create(req.user, req.body);
        res.json(tweet);
    });

Router.route('/:author')
    .get(async (req, res, next) => {
        const tweets = await TweetService.getByAuthor(req.params.author);
        res.json(tweets);
    });

Router.route('/me/subscriptions')
    .get(async (req, res, next) => {
        const tweets = await TweetService.getBySubscriptions(req.user);
        res.json(tweets);
    });

Router.route('/:id/like')
    .post(async (req, res, next) => {
        const tweet = await TweetService.like(req.user, req.params.id);
        res.json(tweet);
    });
Router.route('/:id/unlike')
    .post(async (req, res, next) => {
        const tweet = await TweetService.unlike(req.user, req.params.id);
        res.json(tweet);
    });

module.exports = Router;
