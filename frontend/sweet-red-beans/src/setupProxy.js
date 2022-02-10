const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
    app.use(
        '/transactions',
        createProxyMiddleware({
            target: "http://localhost:8080",
            changeOrigin: true,
            // pathRewrite: {
            //     '^/transactions': 'http://localhost:8080/transactions' // 하위 url 초기화
            // }

        })

    );
};