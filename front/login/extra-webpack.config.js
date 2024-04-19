const singleSpaAngularWebpack =
  require("single-spa-angular/lib/webpack").default;
const ModuleFederationPlugin = require("webpack/lib/container/ModuleFederationPlugin");
const WebpackRemoteTypesPlugin = require("webpack-remote-types-plugin").default;

module.exports = (config, options) => {
  config.output = {
    ...config.output,
    uniqueName: "login",
  };
  config.optimization = { ...config.optimization, runtimeChunk: false };
  config.output = {
    ...config.output,
    publicPath: "http://localhost:4200/"
  }
  config.plugins = [
    ...config.plugins,
    new WebpackRemoteTypesPlugin({
      remotes: {
        "@shared": 'shared@http://localhost:4300/',
      },
      outputDir: './', // supports [name] as the remote name
      remoteFileName: 'shared-dts.tgz', // default filename is [name]-dts.tgz where [name] is the remote name, for example, `app` with the above setup
    }),
    new ModuleFederationPlugin({
      shared: {
        "@angular/core": {
          singleton: true,
          strictVersion: true,
          requiredVersion: "^16.2.0",
        },
        "@angular/common": {
          singleton: true,
          strictVersion: true,
          requiredVersion: "^16.2.0",
        },
        "@angular/router": {
          singleton: true,
          strictVersion: true,
          requiredVersion: "^16.2.0",
        },
        rxjs: {
          singleton: true,
          strictVersion: true,
          requiredVersion: "~7.8.0",
        },
        "single-spa": {
          singleton: true,
          strictVersion: true,
          requiredVersion: "^6.0.1",
        },
        "single-spa-angular": {
          singleton: true,
          strictVersion: true,
          requiredVersion: "^9.0.1",
        },
        "primeflex":{
          singleton: true,
          strictVersion: true,
          requiredVersion: "^3.3.1",
        },
        "primeng": {
          singleton: true,
          strictVersion: true,
          requiredVersion: "^17.12.0",
        },
        "primeicons": {
          singleton: true,
          strictVersion: true,
          requiredVersion: "^7.0.0",
        },
      },
      remotes:{
        "@shared" : "shared@http://localhost:4300",
      },
      name: "login",
      filename: "remoteEntry.js",
      exposes: {
        "./module": "./src/main.single-spa.ts",
      },
    }),
  ];

  const singleSpaWebpackConfig = singleSpaAngularWebpack(config, options);

  // Feel free to modify this webpack config however you'd like to
  return singleSpaWebpackConfig;
};