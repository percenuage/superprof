const express = require('express');
const morgan = require('morgan');
const status = require('redcactus');
const api = require('./api');

/* ---------- APPLICATION ---------- */

const app = express();

/* ---------- ROUTES ---------- */

const routes = async (req, res, next) => {
    try {
        const results = await api.getLastResults(req.query);
        await res.json(results);
    } catch (e) {
        console.error(e);
        next(e);
    }
};

/* ---------- CONFIGURATIONS ---------- */

app.use(morgan('dev'));
app.use(express.static('./public'));
app.use('/status', status());
app.use('/api/nba', routes);

/* ---------- START ---------- */

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.info(`Server listening @ localhost:${PORT}`));