'use strict';

const Comment = require('./comment.model');
const TweetService = require('../tweet/tweet.service');

class CommentService {

    static getAll() {
        return Comment.find();
    }

    static async create(auth, tweetId, message) {
        const comment = await Comment.create({tweetId, message, author: auth.pseudo});
        await TweetService.addComment(comment);
        return comment;
    }
}

module.exports = CommentService;
