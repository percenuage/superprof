'use strict';

const User = require('./user.model');
const Bcrypt = require('bcryptjs');

class UserService {
    static getAll() {
        return User.find();
    }

    static create(user) {
        user.password = Bcrypt.hashSync(user.password);
        return User.create(user);
    }

    static getByPseudo(pseudo) {
        return User.findOne({pseudo});
    }

    static async subscribe(auth, pseudo) {
        auth = await User.findOneAndUpdate({pseudo: auth.pseudo}, {$addToSet: {subscriptions: pseudo}}, {new: true});
        await User.findOneAndUpdate({pseudo}, {$addToSet: {followers: auth.pseudo}});
        return auth;
    }

    static async unsubscribe(auth, pseudo){
        auth = await User.findOneAndUpdate({pseudo: auth.pseudo}, {$pull: {subscriptions: pseudo}}, {new: true});
        await User.findOneAndUpdate({pseudo}, {$pull: {followers: auth.pseudo}});
        return auth;
    }

    static comparePassword(clear, hash) {
        return Bcrypt.compareSync(clear, hash);
    }
}

module.exports = UserService;
