const path = require('path');
const webpack = require('webpack');

module.exports = {
    entry: './src/main/assets/app.js',
    output: {
        filename: 'app.js',
        publicPath: path.resolve(__dirname, './src/main/resources/static/build/'),
        path: path.resolve(__dirname, './src/main/resources/static/build'),
    },
    plugins: [
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery",
            'window.jQuery': 'jquery',
        })
    ],
    resolveLoader: {
        modules: [
            path.join(__dirname, 'node_modules')
        ]
    },
    resolve: {
        modules: [
            path.join(__dirname, 'node_modules')
        ],
        alias: {
            jquery: "jquery/src/jquery"
        },
    },

    mode: "development"
};