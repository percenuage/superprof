'use strict';

const Tweet = require('./tweet.model');

class TweetService {
    static getAll() {
        return Tweet.find();
    }
    static getById(id){
        return Tweet.findById(id);
    }

    static create(auth, tweet) {
        return Tweet.create({author: auth.pseudo, message: tweet.message});
    }

    static getByAuthor(author) {
        return Tweet.find({author});
    }

    static async getBySubscriptions(auth) {
        return Tweet.find({author: {$in: auth.subscriptions}});
    }

    static async like(auth, id) {
        return Tweet.findByIdAndUpdate(id, {$addToSet: {likes: auth.pseudo}}, {new: true});
    }

    static async unlike(auth, id){
        return Tweet.findByIdAndUpdate(id, {$pull: {likes: auth.pseudo}}, {new: true});

    }

    static async addComment(comment) {
        return Tweet.findByIdAndUpdate(comment.tweetId, {$addToSet: {comments: comment}}, {new: true});
    }

}
module.exports = TweetService;
