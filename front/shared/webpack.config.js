const { merge } = require("webpack-merge");
const singleSpaDefaults = require("webpack-config-single-spa-ts");
const ModuleFederationPlugin = require("webpack/lib/container/ModuleFederationPlugin");


const path = require("path");


module.exports = (webpackConfigEnv, argv) => {
  const defaultConfig = singleSpaDefaults({
    orgName: "MobyDigital",
    projectName: "shared",
    webpackConfigEnv,
    argv,
  });

  return merge(defaultConfig, {
    // modify the webpack config however you'd like to by adding to this object
    output: {
      publicPath: "http://localhost:4300/",
    },
    devServer:{
      /*server: {
        type: 'https',
        options: {
          key: "./ssl/server.key",
          cert: "./ssl/server.crt",
        },
      },*/
      static: [
        {
          directory: path.join(__dirname, "./public"),
        },
        {
          directory: path.join(__dirname, "./src/assets"),
        }
      ],
      port: 4300
    },
    plugins:[
      new ModuleFederationPlugin({
        name: "shared",
        filename: "remoteEntry.js",
        exposes: {
          "./jwt": "./src/jwt",
        },
      })
    ],
  
  });
};
