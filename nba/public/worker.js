const CACHE_NAME = 'nba';
const CACHE_FILES = [
    '/',
    '/favicon.ico',
    '/manifest.json',
    '/worker.js',
    '/index.html',
    '/app.js',
    '/index.css',
    '/images/icons/icon-72x72.png',
    '/images/icons/icon-96x96.png',
    '/images/icons/icon-128x128.png',
    '/images/icons/icon-144x144.png',
    '/images/icons/icon-152x152.png',
    '/images/icons/icon-192x192.png',
    '/images/icons/icon-384x384.png',
    '/images/icons/icon-512x512.png',
];

self.addEventListener('install', e => {
    console.log('[ServiceWorker] Install');
    e.waitUntil(addCache())
});

const addCache = async () => {
    console.log('[ServiceWorker] Caching app shell');
    const cache = await caches.open(CACHE_NAME);
    return cache.addAll(CACHE_FILES);
};

self.addEventListener('fetch', e => {
    console.log('[ServiceWorker] Fetch : Cache First');
    if(e.request.url.includes("/api/")){
        e.respondWith(handleRestApiRequest(e));
        e.waitUntil(updateRestApiCache(e.request)/*.then(resfreshRestApi)*/);
    } else {
        e.respondWith(handleStaticRequest(e));
    }
});

const handleRestApiRequest = async (event) => {
    console.log('[ServiceWorker] Rest Api Request: ', event.request.url);
    return caches.match(event.request);
};

const updateRestApiCache = async (req) => {
    const res = await fetch(req.url);
    if (!res.ok) {
        throw new Error('Network error');
    }
    const cache = await caches.open(CACHE_NAME);
    await cache.put(req, res.clone());
    return res;
};

const resfreshRestApi = async (res) => {
    const json = await res.json();
    const clients = await self.clients.matchAll();
    self.clients.forEach(client => {
        client.postMessage(JSON.stringify({type: res.url, data: json.data}));
    });
    return json.data;
};

const handleStaticRequest = async (event) => {
    console.log('[ServiceWorker] Static Request: ', event.request.url);
    const cached = await caches.match(event.request);
    return cached || fetch(event.request);
};

self.addEventListener('activate', e => {
    e.waitUntil(cleanCache());
});

const cleanCache = async () => {
    console.log('[ServiceWorker] Clean cache');
    const keys = await caches.keys();
    const deletedKeys = keys.filter(key => key !== CACHE_NAME).map(key => caches.delete(key));
    return Promise.all(deletedKeys);
};

/*self.addEventListener('sync', e => {
    console.log('[ServiceWorker] Sync');
    if (e.tag === 'syncNba') {
        e.waitUntil(syncNba());
    }
});

const syncNba = async () => {
    return update({ url: `https://reqres.in/api/users` })
        .then(refresh)
        .then((attendees) => self.registration.showNotification(
            `${attendees.length} attendees to the PWA Workshop`
        ))
};*/
