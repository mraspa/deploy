const { merge } = require("webpack-merge");
const singleSpaDefaults = require("webpack-config-single-spa-ts");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const fs = require("fs");
const ModuleFederationPlugin = require("webpack/lib/container/ModuleFederationPlugin");

module.exports = (webpackConfigEnv, argv) => {
  const orgName = "MobyDigital";
  const defaultConfig = singleSpaDefaults({
    orgName,
    projectName: "root-config",
    webpackConfigEnv,
    argv,
    disableHtmlGeneration: true,
  });

  return merge(defaultConfig, {
    // modify the webpack config however you'd like to by adding to this object
    // devServer:{
    //   server: {
    //     type: 'http',
    //     options: {
    //       key: "./ssl/server.key",
    //       cert: "./ssl/server.crt",
    //      /// devContentSecurityPolicy: "connect-src 'self' http://localhost:8080 'unsafe-eval'",
    //     },
    //   }
    // },
    plugins: [
      new ModuleFederationPlugin({
        name: "root",
        library: { type: "var" },
        filename: "remoteEntry.js",
        remotes: {
          "@login": "login",
          "@onboarding": "onboarding",
          "@lifestyle": "lifestyle",
          "@wallet": "wallet",
          "@header": "header",
          "@footer": "footer",
        },
        exposes: {},
        shared: ["single-spa"],
      }),
      new HtmlWebpackPlugin({
        inject: false,
        template: "src/index.ejs",
        templateParameters: {
          isLocal: webpackConfigEnv && webpackConfigEnv.isLocal,
          orgName,
        },
      }),
    ],
  });
};
