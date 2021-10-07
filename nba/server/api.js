const axios = require('axios');

const getLastResults = async (params) => {
    const config = {
        baseURL: 'https://free-nba.p.rapidapi.com',
        headers: {
            'x-rapidapi-host': 'free-nba.p.rapidapi.com',
            'x-rapidapi-key': '2b317853c3msh3584b20be6b7961p12c565jsn6b6f58673c80'
        },
        params
    };
    const response = await axios('/games', config);
    return parseResults(response.data);
};

const parseResults = (data) => {
    const games = data.data;
    return games.map(game => {
        return {
            date: game.date,
            season: game.season,
            status: game.status,
            period: game.period,
            home_team_name: game.home_team.full_name,
            home_team_score: game.home_team_score,
            visitor_team_name: game.visitor_team.full_name,
            visitor_team_score: game.visitor_team_score,
        }
    })
};

module.exports.getLastResults = getLastResults;

