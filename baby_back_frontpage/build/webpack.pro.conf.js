'use strict'
const utils = require('./utils')
const webpack = require('webpack')
const config = require('../config')
const merge = require('webpack-merge')
const path = require('path')
const baseWebpackConfig = require('./webpack.base.conf')
const CopyWebpackPlugin = require('copy-webpack-plugin')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const FriendlyErrorsPlugin = require('friendly-errors-webpack-plugin')
const portfinder = require('portfinder')


const HOST = process.env.HOST
const PORT = process.env.PORT && Number(process.env.PORT)

const proWebpackConfig = merge(baseWebpackConfig, {
  module: {
    rules: utils.styleLoaders({ sourceMap: config.pro.cssSourceMap, usePostCSS: true })
  },
  // cheap-module-eval-source-map is faster for proelopment
  devtool: config.pro.devtool,

  // these proServer options should be customized in /config/index.js
  proServer: {
    clientLogLevel: 'warning',
    historyApiFallback: {
      rewrites: [
        { from: /.*/, to: path.posix.join(config.pro.assetsPublicPath, 'index.html') },
      ],
    },
    hot: true,
    contentBase: false, // since we use CopyWebpackPlugin.
    compress: true,
    host: HOST || config.pro.host,
    port: PORT || config.pro.port,
    open: config.pro.autoOpenBrowser,
    overlay: config.pro.errorOverlay
      ? { warnings: false, errors: true }
      : false,
    publicPath: config.pro.assetsPublicPath,
    proxy: config.pro.proxyTable,
    quiet: true, // necessary for FriendlyErrorsPlugin
    watchOptions: {
      poll: config.pro.poll,
    }

  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': require('../config/pro.env')
    }),
    new webpack.HotModuleReplacementPlugin(),
    new webpack.NamedModulesPlugin(), // HMR shows correct file names in console on update.
    new webpack.NoEmitOnErrorsPlugin(),
    // https://github.com/ampedandwired/html-webpack-plugin
    new HtmlWebpackPlugin({
      filename: 'index.html',
      template: 'index.html',
      inject: true
    }),
    // copy custom static assets
    new CopyWebpackPlugin([
      {
        from: path.resolve(__dirname, '../static'),
        to: config.pro.assetsSubDirectory,
        ignore: ['.*']
      }
    ])
  ]
})

module.exports = new Promise((resolve, reject) => {
  portfinder.basePort = process.env.PORT || config.pro.port
  portfinder.getPort((err, port) => {
    if (err) {
      reject(err)
    } else {
      // publish the new Port, necessary for e2e tests
      process.env.PORT = port
      // add port to proServer config
      proWebpackconfig.proServer.port = port

      // Add FriendlyErrorsPlugin
      proWebpackConfig.plugins.push(new FriendlyErrorsPlugin({
        compilationSuccessInfo: {
          messages: [`Your application is running here: http://${proWebpackconfig.proServer.host}:${port}`],
        },
        onErrors: config.pro.notifyOnErrors
        ? utils.createNotifierCallback()
        : undefined
      }))

      resolve(proWebpackConfig)
    }
  })
})
