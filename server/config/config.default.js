/* eslint valid-jsdoc: "off" */

'use strict';

/**
 * @param {Egg.EggAppInfo} appInfo app info
 */
module.exports = appInfo => {
  /**
   * built-in config
   * @type {Egg.EggAppConfig}
   **/
  const config = exports = {};

  config.security = { // 关闭 csrf
  // https://github.com/eggjs/egg/issues/509

    csrf: {
      enable: false,
    },
  };

  // use for cookie sign key, should change to your own and keep security
  config.keys = appInfo.name + '_1569405297872_4607';

  // add your middleware config here
  config.middleware = [];

  // add your user config here
  const userConfig = {
    // myAppName: 'egg',
  };

  return {
    ...config,
    ...userConfig,
  };
};
