'use strict';

const Mongoose = require('mongoose');
const Comment = require('../comment/comment.model');

const options = {
    timestamps: true,
};

const TweetSchema = new Mongoose.Schema({

    message: {type: String, required: true},
    author: {type: String, required: true, trim: true, lowercase: true},
    comments: [Comment.schema],
    likes: [String],
    tags: [],

}, options);

module.exports = Mongoose.model('Tweet', TweetSchema);
