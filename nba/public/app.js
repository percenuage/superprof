console.log('NBA App');

const init = async () => {
    // Load games from local server
    const games = await getAllGames();

    // Instanciate app vue
    new Vue({
        el: '#nba-scores',
        data: { games: games }
    });

    // Register service worker
    await registerSW();
};

const getAllGames = async () => {
    let games = [];
    try {
        const response = await fetch('/api/nba');
        games = await response.json();
    } catch (err) {
        console.error(err);
    }
    return games;
};

const registerSW = async () => {
    if('serviceWorker' in navigator) {
        try {
            await navigator.serviceWorker.register('/worker.js');
            console.log('[ServiceWorker] Registered')
        } catch (e) {
            console.log('[ServiceWorker] Registration failed with ' + e)
        }
    }
};

/*
const registerNotification = () => {
    Notification.requestPermission(permission => {
        if (permission !== 'granted') {
            console.error('Permission was not granted.')
        }
        registerBackgroundSync();
    })
};

const registerBackgroundSync = async () => {
    if (!navigator.serviceWorker){
        return console.error("Service Worker not supported")
    }
    try {
        const registration = await navigator.serviceWorker.ready;
        await registration.sync.register('syncNba');
    } catch (e) {
        console.error('Error registering background sync', e)
    }
};
*/

navigator.serviceWorker.onmessage = e => {
    const message = JSON.parse(e.data);
    if( message && message.type.includes("/api/nba")){
        console.log('Refresh games: ', message.data)
    }
};

(async () => {
    await init();
})();





