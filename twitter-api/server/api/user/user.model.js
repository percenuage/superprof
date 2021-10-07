'use strict';

const Mongoose = require('mongoose');

const options = {
    timestamps: true,
};

const UserSchema = new Mongoose.Schema({

    firstName: {type: String, required: true, trim: true},
    lastName: {type: String, required: true, trim: true},
    email: {type: String, trim: true},
    pseudo: {type: String, required: true, unique: true, trim: true, lowercase: true},
    password: {type: String, required: true},
    pictureUrl: String,
    description: String,
    followers: [String],
    subscriptions: [String],

}, options);

module.exports = Mongoose.model('User', UserSchema);
