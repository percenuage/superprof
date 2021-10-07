'use strict';
const Mongoose = require('mongoose');

const options = {
    timestamps: true,
};

const CommentSchema = new Mongoose.Schema({
    tweetId: {type: String, required: true},
    author: {type: String, required: true, trim: true, lowercase: true},
    message:{type: String, required: true},
}, options);

module.exports = Mongoose.model('Comment', CommentSchema);
