'use strict';

const Express = require('express');
const Session = require('express-session');
const BodyParser = require('body-parser');
const Morgan = require('morgan');
const Passport = require('passport');

/* ---------- APPLICATION ---------- */

const app = Express();

/* ---------- ROUTES ---------- */

const AuthRoute = require('./api/auth/auth.route');
const UserRoute = require('./api/user/user.route');
const TweetRoute = require('./api/tweet/tweet.route');
const CommentRoute = require('./api/comment/comment.route');

/* ---------- CONFIGURATIONS ---------- */

const Middleware = require('./middleware');
const PassportConfig = require('./passport');
PassportConfig();

app.use(Morgan('dev'));
app.use(BodyParser.urlencoded({ extended: true }));
app.use(BodyParser.json());
app.use(Session({
    resave: true, saveUninitialized: true,
    secret: process.env.SESSION_SECRET
}));
app.use(Passport.initialize());
app.use(Passport.session());
app.use(Express.static('./client'));

app.route('/status').get((req, res) => res.send('Server Express listening'));

app.use('/api/auth', AuthRoute);
app.use('/api/users', Middleware.isAuthenticated, UserRoute);
app.use('/api/tweets', Middleware.isAuthenticated, TweetRoute);
app.use('/api/comments', Middleware.isAuthenticated, CommentRoute);

app.use(Middleware.errorHandler);

/* ---------- ROUTES ---------- */

app.start = port => app.listen(port, () => console.info(`[${process.env.NODE_ENV}] Server listening @ localhost:${port}`));

module.exports = app;
