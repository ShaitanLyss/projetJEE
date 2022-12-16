const path = require('path');
const Encore = require('@symfony/webpack-encore');
const webpack = require('webpack')
const CircularDependencyPlugin = require('circular-dependency-plugin');


// Manually configure the runtime environment if not already configured yet by the "encore" command.
// It's useful when you use tools that rely on webpack.config.js file.
if (!Encore.isRuntimeEnvironmentConfigured()) {
    Encore.configureRuntimeEnvironment(process.env.NODE_ENV || 'dev');
}

Encore
    .setOutputPath("src/main/resources/static/build")
    .setPublicPath('/build')

Encore
    // directory where compiled assets will be stored
    .setOutputPath('public/build/')
    // public path used by the web server to access the output path
    .setPublicPath('/build')
    // only needed for CDN's or sub-directory deploy
    //.setManifestKeyPrefix('build/')

    /*
     * ENTRY CONFIG
     *
     * Each entry will result in one JavaScript file (e.g. app.js)
     * and one CSS file (e.g. app.css) if your JavaScript imports CSS.
     */
    .addEntry('app', './src/main/assets/app.js')

    // enables the Symfony UX Stimulus bridge (used in assets/bootstrap.js)
    .enableStimulusBridge('./src/main/assets/controllers.json')

    // When enabled, Webpack "splits" your files into smaller pieces for greater optimization.
    .splitEntryChunks()

    // will require an extra script tag for runtime.js
    // but, you probably want this, unless you're building a single-page app
    .enableSingleRuntimeChunk()

    /*
     * FEATURE CONFIG
     *
     * Enable & configure other features below. For a full
     * list of features, see:
     * https://symfony.com/doc/current/frontend.html#adding-more-features
     */
    .cleanupOutputBeforeBuild()
    .enableBuildNotifications()
    .enableSourceMaps(!Encore.isProduction())
    // enables hashed filenames (e.g. app.abc123.css)
    .enableVersioning(Encore.isProduction())

    .configureBabel((config) => {
        config.plugins.push('@babel/plugin-proposal-class-properties');
    })

    // enables @babel/preset-env polyfills
    .configureBabelPresetEnv((config) => {
        config.useBuiltIns = 'usage';
        config.corejs = 3;
    })

    .addAliases({
            jquery: "jquery/src/jquery",
            datatables: "datatables.net"
        })

// enables Sass/SCSS support
//.enableSassLoader()

// uncomment if you use TypeScript
//.enableTypeScriptLoader()

// uncomment if you use React
//.enableReactPreset()

// uncomment to get integrity="..." attributes on your script & link tags
// requires WebpackEncoreBundle 1.4 or higher
//.enableIntegrityHashes(Encore.isProduction())

// uncomment if you're having problems with a jQuery plugin
.autoProvidejQuery()
    .addPlugin(new CircularDependencyPlugin({
            onStart({ compilation }) {
                console.log('start detecting webpack modules cycles');
            },
            // `onDetected` is called for each module that is cyclical
            onDetected({ module: webpackModuleRecord, paths, compilation }) {
                // `paths` will be an Array of the relative module paths that make up the cycle
                // `module` will be the module record generated by Encore that caused the cycle
                compilation.errors.push(new Error(paths.join(' -> ')))
            },
            // `onEnd` is called before the cycle detection ends
            onEnd({ compilation }) {
                console.log('end detecting webpack modules cycles');
            },
            // exclude detection of files based on a RegExp
            exclude: /a\.js|node_modules/,
            // include specific files based on a RegExp
            include: /dir/,
            // add errors to Encore instead of warnings
            failOnError: true,
            // allow import cycles that include an asyncronous import,
            // e.g. via import(/* webpackMode: "weak" */ './file.js')
            allowAsyncCycles: false,
            // set the current working directory for displaying module paths
            cwd: process.cwd(),
        }))
;
const config = Encore.getWebpackConfig();
// config.module.rules.unshift({
//     parser: {
//         amd: false
//     }
// })
module.exports = config;

// module.exports += {
//     // entry: './src/main/assets/app.js',
//     // output: {
//     //     filename: 'app.js',
//     //     publicPath: path.resolve(__dirname, './src/main/resources/static/build/'),
//     //     path: path.resolve(__dirname, './src/main/resources/static/build'),
//     // },
//     plugins: [
//         new webpack.ProvidePlugin({
//             // $: "jquery",
//             // jQuery: "jquery",
//             // 'window.jQuery': 'jquery',
//             '$.fn.dataTable': 'datatables'
//         }),
//
//     ],
//     resolveLoader: {
//         modules: [
//             path.join(__dirname, 'node_modules')
//         ]
//     },
//     // loaders: [ { test: /myjsfile.js/, loader: 'imports?define=>false'} ],
//     // module: {
//     //     rules: [{
//     //         test: /\.js/,
//     //         use: [
//     //             {
//     //                 loader: "imports-loader",
//     //                 options: {
//     //                     imports: [
//     //                         "side-effects datatables.net",
//     //                         "side-effects datatables.net-dt",
//     //                     ]
//     //                 }
//     //             }
//     //         ]
//     //     }],
//     // },
//
//     resolve: {
//         modules: [
//             path.join(__dirname, 'node_modules')
//         ],
//         alias: {
//             jquery: "jquery/src/jquery",
//             datatables: "datatables.net"
//         },
//     },
//
//     // mode: "development"
// };